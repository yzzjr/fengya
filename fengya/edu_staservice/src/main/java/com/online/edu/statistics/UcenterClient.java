package com.online.edu.statistics;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Component
@FeignClient("fengya-ucservice")
public interface UcenterClient {

        @GetMapping("/ucenter/member/countRegisterNum/{day}")
        public R countRegisterNum(@PathVariable("day") String day);


}
