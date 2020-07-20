package com.online.edu.vidservice;


import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient("fengya-staservice")
public interface StatisClient {

    @GetMapping("/statistics/daily/incRegisterNum")
    public R incRegisterNum();

    @GetMapping("/statistics/daily/incLoginNum")
    public R incLoginNum();

    @GetMapping("/statistics/daily/incVideoViewNum")
    public R incVideoViewNum();

    @GetMapping("/statistics/daily/incCourseNum")
    public R incCourseNum();

}
