package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.client.VidClient;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    //根据课程id删除小节

    @Autowired
    private VidClient vidClient;

    @Override
    public void deleteVideoByCourseId(String id) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        List<String> videoIdList = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            videoIdList.add(videoSourceId);
        }
        vidClient.removeMoreVideoAliyun(videoIdList);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }

    //删除小节
    @Override
    public boolean removeVideo(String unitId) {
        //获取视频Id
        EduVideo eduVideo = baseMapper.selectById(unitId);
        String videoId = eduVideo.getVideoSourceId();

        //TODO删除阿里云视频
        if(!StringUtils.isEmpty(videoId)){
            vidClient.removeVideoAliyunId(videoId);
        }

        int result = baseMapper.deleteById(unitId);

        return result>0;
    }

    @Override
    public String getCourseByVid(String videoId) {

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("video_source_id",videoId);
        EduVideo eduVideo = baseMapper.selectOne(wrapperVideo);
        String courseId = eduVideo.getCourseId();
        return courseId;
    }


}
