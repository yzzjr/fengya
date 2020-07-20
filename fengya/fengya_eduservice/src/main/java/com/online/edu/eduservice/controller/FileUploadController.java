package com.online.edu.eduservice.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.online.edu.common.R;
import com.online.edu.eduservice.handler.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/eduservice/oss")
@CrossOrigin
public class FileUploadController {

    //上传头像操作
    @PostMapping("upload")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file,@RequestParam(value = "host",required = false) String host){
        //获取上传文件名称，获取上传文件输入流
        String endpoint = ConstantPropertiesUtils.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSECRET;
        String bucketName = ConstantPropertiesUtils.BUCKETNAME;

        try{
            MultipartConfigFactory factory = new MultipartConfigFactory();
            factory.setMaxFileSize("10240KB");
            String filename  = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String hostName = ConstantPropertiesUtils.HOST;
            if(!StringUtils.isEmpty(host)){
                hostName = host;
            }
            InputStream in = file.getInputStream();

            filename = uuid+filename;
            String filepath = new DateTime().toString("yyyy/MM/dd");
            //filename = filepath+"/"+filename;
            filename = filepath+"/"+hostName+"/"+filename;
            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            ossClient.putObject(bucketName, filename, in);
            // 关闭OSSClient。
            ossClient.shutdown();

             String path = "https://"+ bucketName+"."+endpoint+"/"+filename;

            //https://fengya-teacher.http//oss-cn-beijing.aliyuncs.com/2020/04/01/8be112a0-fd2e-43ce-8989-8a54a722257cfile.png
            return R.ok().data("imgurl",path);
            //https://fengya-teacher.oss-cn-beijing.aliyuncs.com/2020/04/01/8be112a0-fd2e-43ce-8989-8a54a722257cfile.png
            //https://fengya-teacher.oss-cn-beijing.aliyuncs.com/%E6%8D%95%E8%8E%B7.PNG
        }catch (Exception e){
            e.printStackTrace();
            return R.error();


        }




    }


}
