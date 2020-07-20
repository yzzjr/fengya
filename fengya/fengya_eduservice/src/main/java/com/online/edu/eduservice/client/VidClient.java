package com.online.edu.eduservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("fengya-vidservice")
public interface VidClient {

    //定义调用的放大
    @DeleteMapping("/vidservice/vod/{videoId}")
    public R removeVideoAliyunId(@PathVariable("videoId") String videoId);

    @DeleteMapping("/vidservice/vod/removeMoreVideo")
    public R removeMoreVideoAliyun(@RequestParam("videoList") List videoList);

}
