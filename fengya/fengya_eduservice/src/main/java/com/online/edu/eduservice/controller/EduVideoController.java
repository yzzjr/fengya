package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save  = eduVideoService.save(eduVideo);
        if(save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("getVideoId/{videoId}")
    public R getVideoId(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean result = eduVideoService.updateById(eduVideo);
        if(result){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @DeleteMapping("deleteVideoId/{videoId}")
    public R deleteVideoId(@PathVariable String videoId){
        boolean flag =  eduVideoService.removeVideo(videoId);
        if(flag){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("getCourseIdByVid")
    public String getCourseId(String vid){
        String courseId = eduVideoService.getCourseByVid(vid);
        return courseId;
    }
}

