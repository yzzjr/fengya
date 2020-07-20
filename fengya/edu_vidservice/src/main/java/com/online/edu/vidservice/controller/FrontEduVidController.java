package com.online.edu.vidservice.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.online.edu.common.R;
import com.online.edu.vidservice.StatisClient;
import com.online.edu.vidservice.entity.CheckDto;
import com.online.edu.vidservice.service.VidService;
import com.online.edu.vidservice.utils.AliyunVodSDKUtils;
import com.online.edu.vidservice.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vidservice/frontVideo")
@CrossOrigin
public class FrontEduVidController {
    @Autowired
    private VidService vidService;

    @Autowired
    private StatisClient statisClient;


    @PostMapping("validate")
    public R validate(@RequestBody CheckDto checkInfo){


        String vid = checkInfo.getVideoSourceId();

        String token = checkInfo.getToken();
        Integer result = vidService.check(vid,token);
        if(result == 0){
            System.out.println("购买课程");
            return R.error().data("result",1);
        }

        else {
            return R.ok();
        }

    }


    @PostMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid, @RequestBody String token){

        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            request.setAuthInfoTimeout(200L);
            //ÉèÖÃÇëÇó²ÎÊý
            request.setVideoId(vid);
            //»ñÈ¡ÇëÇóÏìÓ¦
            response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();
            if(playAuth!=null){
                R r = statisClient.incVideoViewNum();
                return R.ok().data("playAuth",playAuth);
            }
            else {
                return R.error();
            }

        } catch (Exception e) {
            return R.error();
        }


    }


}
