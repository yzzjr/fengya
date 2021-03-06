package com.online.edu.ucenter.controller;




import com.online.edu.common.R;
import com.online.edu.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping("countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable String day){
        Integer result =memberService.countRegister(day);
        return R.ok().data("registerCount",result);
    }



}

