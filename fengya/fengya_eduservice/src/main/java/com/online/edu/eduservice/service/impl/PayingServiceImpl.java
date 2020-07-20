package com.online.edu.eduservice.service.impl;


import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.online.edu.eduservice.config.Configs;
import com.online.edu.eduservice.handler.ConstantPropertiesUtils;
import com.online.edu.eduservice.model.GoodsDetail;
import com.online.edu.eduservice.model.builder.AlipayTradePrecreateRequestBuilder;
import com.online.edu.eduservice.model.builder.AlipayTradeQueryRequestBuilder;
import com.online.edu.eduservice.model.result.AlipayF2FPrecreateResult;
import com.online.edu.eduservice.model.result.AlipayF2FQueryResult;
import com.online.edu.eduservice.service.AlipayMonitorService;
import com.online.edu.eduservice.service.AlipayTradeService;
import com.online.edu.eduservice.service.EduUserCourseService;
import com.online.edu.eduservice.service.PayingService;
import com.online.edu.eduservice.utils.ZxingUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
public class PayingServiceImpl implements PayingService{

    private static Log log = LogFactory.getLog(PayingServiceImpl.class);

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService   tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }

    @Override
    public String trade_precreate(String orderId,Integer Amount, String courseId, String storeName, String userId) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = orderId;
        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "风雅艺术学校课程购买";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = Amount.toString();

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "商品共消费"+Amount+"元";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "fengyayishu";

        // 支付超时，定义为120分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance(courseId, storeName, Amount, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);



        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setSellerId(sellerId).setBody(body)
                .setStoreId(storeId).setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                //dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format("F:\\qrcode\\"+orderId+".png",
                        response.getOutTradeNo());

                log.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                String ossFilePath = this.uploadQRCode(filePath,orderId,orderId);
                return ossFilePath;


            case FAILED:
                log.error("支付宝预下单失败!!!");
                return null;


            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return null;


            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return null;

        }
    }

    @Autowired
    private EduUserCourseService eduUserCourseService;

    @Override
    public String createOrder(String courseId, String userId) {
        return eduUserCourseService.add(courseId, userId);
    }

    @Override
    public Boolean getOrderStatus(String orderId) {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        Boolean flag = false;

        String outTradeNo = orderId;
        System.out.println("check begin");
        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                flag = eduUserCourseService.modifyStatus(orderId);

                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }

        return flag;
    }


    public String uploadQRCode(String filePath,String orderId,String fileName) {
        //获取上传文件名称，获取上传文件输入流
        String endpoint = ConstantPropertiesUtils.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSECRET;
        String bucketName = ConstantPropertiesUtils.BUCKETNAME;

        try{
            MultipartConfigFactory factory = new MultipartConfigFactory();
            factory.setMaxFileSize("10240KB");
            File file = new File(filePath);
            String filename  = fileName;
            String uuid = orderId;
            String hostName = "order";



            filename = uuid+filename;
            String filepath = new DateTime().toString("yyyy/MM/dd");
            //filename = filepath+"/"+filename;
            filename = filepath+"/"+hostName+"/"+filename;
            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            ossClient.putObject(bucketName, filename, file);
            // 关闭OSSClient。
            ossClient.shutdown();

            String path = "https://"+ bucketName+"."+endpoint+"/"+filename;

            //https://fengya-teacher.http//oss-cn-beijing.aliyuncs.com/2020/04/01/8be112a0-fd2e-43ce-8989-8a54a722257cfile.png
            System.out.println(path);
            return path;
            //https://fengya-teacher.oss-cn-beijing.aliyuncs.com/2020/04/01/8be112a0-fd2e-43ce-8989-8a54a722257cfile.png
            //https://fengya-teacher.oss-cn-beijing.aliyuncs.com/%E6%8D%95%E8%8E%B7.PNG
        }catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }



}
