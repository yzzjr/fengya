package com.online.edu.vidservice.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.online.edu.common.R;
import com.online.edu.vidservice.service.VidService;
import com.online.edu.vidservice.utils.AliyunVodSDKUtils;
import com.online.edu.vidservice.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vidservice/vod")
@CrossOrigin
public class VidController {

    @Autowired
    private VidService vidService;



    @PostMapping("upload")
    public R uploadAliyunVideo(@RequestParam("file") MultipartFile file){

        String videoId = vidService.uploadVideoAliyun(file);
        if(videoId == null){
            return R.error();
        }
        else {
            return R.ok().data("videoId",videoId);
        }
    }

    @DeleteMapping("{videoId}")
    public R deleteVideoId(@PathVariable String videoId){
        vidService.deleteAliyunVideoId(videoId);
        return R.ok();
    }

    //参数为多个视频参数的list集合
    @DeleteMapping("removeMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoList") List videoList){
        vidService.deleteVideoMore(videoList);
        return R.ok();
    }






}
