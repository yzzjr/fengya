package com.online.edu.eduservice;


import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("fengya-ucservice")
public interface UcenterService {

    @PostMapping("/ucenter/front/member/getIdByToken")
    public R getIdByToken(@RequestParam(value="token") String token);


}
