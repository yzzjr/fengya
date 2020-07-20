package com.online.edu.vidservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.online.edu.common.R;
import com.online.edu.vidservice.client.UserCourseClient;
import com.online.edu.vidservice.service.VidService;
import com.online.edu.vidservice.utils.AliyunVodSDKUtils;
import com.online.edu.vidservice.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;


@Service
public class VidServiceImpl implements VidService{

    @Autowired
    private UserCourseClient userCourseClient;

    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        try{
            System.out.println("uploadVideoAliyun");

            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            System.out.println(title);
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, file.getInputStream());

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            System.out.println(response.getVideoId());
            //System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID


            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;

        }catch (Exception e){
            return null;
        }

    }

    @Override

    public void deleteAliyunVideoId(String videoId) {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置删除视频id
            request.setVideoIds(videoId);
            //调用方法实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        }catch (Exception e){

        }

    }

    @Override
    public void deleteVideoMore(List videoList) {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置删除视频id

            String videoIds = StringUtils.join(videoList,",");

            request.setVideoIds(videoIds);
            //调用方法实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        }catch (Exception e){

        }



    }

    @Override
    public Integer check(String vid, String token) {
        HashMap<String,String> map = new HashMap<>();
        R r = userCourseClient.validate(vid, token);
        return (Integer) r.getData().get("result");
    }
}
