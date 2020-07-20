package com.online.edu.vidservice.client;


import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Component
@FeignClient("fengya-eduservice")
public interface UserCourseClient {

    @PostMapping("/eduservice/eduUserCourse/buyCheck")
    public R validate(@RequestParam(value="vid") String vid,
                      @RequestParam(value = "token") String token);

}
