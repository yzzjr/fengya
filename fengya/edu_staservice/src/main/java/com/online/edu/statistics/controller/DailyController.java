package com.online.edu.statistics.controller;


import com.online.edu.common.R;
import com.online.edu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;


    //获取某一天的注册人数
    @GetMapping("getStatisticsDay/{day}")
    public R getStatisticsDay(@PathVariable String day){

        dailyService.getCountRegisterNum(day);
        return R.ok();
    }

    @GetMapping("getCountData/{type}/{begin}/{end}")
    public R getCountData(@PathVariable String type,
                          @PathVariable String begin,
                          @PathVariable String end){

        Map<String,Object>map = dailyService.getDataCount(type,begin,end);
        return R.ok().data("mapData",map);
    }

    @GetMapping("incRegisterNum")
    public R incRegisterNum(){
        Boolean result = dailyService.addRegister();
        if(result) {
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("incLoginNum")
    public R incLoginNum(){
        Boolean result = dailyService.addLogin();
        if(result) {
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("incVideoViewNum")
    public R incVideoViewNum(){
        Boolean result = dailyService.addVideo();
        if(result) {
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("incCourseNum")
    public R incCourseNum(){
        Boolean result = dailyService.addCourse();
        if(result) {
            return R.ok();
        }
        else {
            return R.error();
        }
    }

}

