package com.online.edu.ucenter.controller;

import com.online.edu.eduservice.handler.EduException;
import com.online.edu.ucenter.utils.ConstantpropertiesUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    //扫描二维码进行回调
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);
        return null;
    }


    @GetMapping("login")
    public String genQrConnect(HttpSession session){
        //微信平台
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //获取扫描后的回调地址
        String redirectUrl = ConstantpropertiesUtils.WX_OPEN_REDIRECT_URL;


        //对回调地址进行url编码

        try{
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        }
        catch(Exception e){
            throw new EduException(20001, e.getMessage());
        }

        //为了测试，这个值传递的是注册内网穿透的域名名称，实现域名跳转
        String state = "zkhwechat";

        //拼接出最终地址
        String qrcoudeUrl= String.format(
                baseUrl,
                ConstantpropertiesUtils.WX_OPEN_APP_ID,
                redirectUrl,
                state
        );


        return "redirect:"+ qrcoudeUrl;


    }



}
