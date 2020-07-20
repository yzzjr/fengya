package com.online.edu.eduservice.controller;



import com.online.edu.common.R;
import com.online.edu.eduservice.service.EduUserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouzhou
 * @since 2020-06-07
 */
@RestController
@RequestMapping("/eduservice/eduUserCourse")
@CrossOrigin
public class EduUserCourseController {

    @Autowired
    EduUserCourseService eduUserCourseService;

    @PostMapping("buyCheck")
    public R validate(@RequestParam(value="vid") String vid,
                      @RequestParam(value = "token") String token){

        Boolean flag = eduUserCourseService.check(vid,token);
        if(flag == false){
            return R.error().data("result",0);
        }
        else {
            return R.ok().data("result",1);
        }
    }

}

