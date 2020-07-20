package com.online.edu.ucenter.controller.Front;

import com.online.edu.common.R;
import com.online.edu.ucenter.StatisClient;
import com.online.edu.ucenter.entity.Member;
import com.online.edu.ucenter.entity.dto.Login;
import com.online.edu.ucenter.entity.dto.Message;
import com.online.edu.ucenter.entity.dto.Register;
import com.online.edu.ucenter.entity.form.MemberInfoForm;
import com.online.edu.ucenter.service.MemberService;
import com.online.edu.ucenter.utils.JwtUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import io.jsonwebtoken.Claims;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/ucenter/front/member")
@CrossOrigin
public class MemberPageController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private StatisClient statisClient;

    private String checkNumber;

    @PostMapping("MessageCheck")
    public R MessageCheck(@RequestBody Message message){
        try{
            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "106159", "c5910769-031f-4333-8de0-ff248d2b1f21");
            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println(message.getMessageMobile());
            params.put("number", message.getMessageMobile());
            params.put("templateId", "793");
            String[] templateParams = new String[2];
            Long check = Math.round((Math.random()+1) * 1000);
            checkNumber = check.toString();
            templateParams[0] = checkNumber;
            templateParams[1] = "5min";
            params.put("templateParams", templateParams);
            String result = client.send(params);
            System.out.println(result);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().data("result","系统出错");
        }

    }

    @PostMapping("memberRegister")
    public R memberRegister(@RequestBody Register register){
        if(register.getCheckNum().equals(checkNumber)){
            Boolean flag = memberService.addMember(register);
            if(flag){
                R r = statisClient.incRegisterNum();
                return R.ok();
            }
            else {
                return R.error();
            }
        }
        else {
            return R.error().data("result",1);
        }
    }

    @PostMapping("memberLogin")
    public R memberLogin(@RequestBody Login login){

        if(memberService.login(login) != null){
            Member member = memberService.login(login);
            String token = JwtUtils.genJsonWebToken(member);
            System.out.println(token);
            R r = statisClient.incLoginNum();
            return R.ok().data("token",token);
        }
        System.out.println("denglushibai");
        return R.error();
    }

    @GetMapping("userInfo/{token}")
    public R getUserInfoToken(@PathVariable String token){
        Claims claims =  JwtUtils.checkJwt(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");

        Member member = new Member();
        member.setId(id);
        member.setAvatar(avatar);
        member.setNickname(nickname);

        return R.ok().data("member",member);
    }

    @PostMapping("getIdByToken")
    public R getIdByToken(@RequestParam(value="token") String token){
        String uid = JwtUtils.getMemberId(token);
        if(uid == null){
            return R.error();
        }
        return R.ok().data("uid",uid);
    }



}
