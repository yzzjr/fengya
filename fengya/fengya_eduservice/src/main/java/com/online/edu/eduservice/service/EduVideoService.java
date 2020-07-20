package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-05
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoByCourseId(String s);

    boolean removeVideo(String videoId);

    String getCourseByVid(String videoId);
}
