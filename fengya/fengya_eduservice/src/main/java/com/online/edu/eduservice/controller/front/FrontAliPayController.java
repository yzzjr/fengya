package com.online.edu.eduservice.controller.front;

import com.online.edu.common.R;
import com.online.edu.eduservice.UcenterService;
import com.online.edu.eduservice.entity.dto.PayCheckInfo;
import com.online.edu.eduservice.entity.dto.PayDto;
import com.online.edu.eduservice.service.PayingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eduservice/AliPay")
@CrossOrigin
public class FrontAliPayController {

    @Autowired
    private PayingService payingService;

    @Autowired
    private UcenterService ucenterService;

    @PostMapping("precreate")
    public R tradePrecreate(@RequestBody PayDto payDto) {
        Integer amount = payDto.getAmount();
        String courseId = payDto.getCourseId();
        String storeName = payDto.getStoreName();
        String token = payDto.getToken();

        R r = ucenterService.getIdByToken(token);
        if(r.getCode() == 20001){
            return R.error().data("result",3);
        }
        String userId = r.getData().get("uid").toString();

        String orderId = payingService.createOrder(courseId, userId);
        String result = payingService.trade_precreate(orderId,amount, courseId, storeName, userId);

        if(!result.equals("")){
            return R.ok().data("address",result).data("orderId",orderId);
        }
        else {
            return R.error().data("result",2);
        }
    }

    @PostMapping("checkStatus")
    public R checkStatus(@RequestBody PayCheckInfo payCheckInfo){
        String orderId = payCheckInfo.getOrderId();
        System.out.println("check begin");
        if(payingService.getOrderStatus(orderId)){
            return R.ok();
        }
        else {
            return R.error();
        }
    }



}


