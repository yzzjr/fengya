package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduUserCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-06-07
 */
public interface EduUserCourseService extends IService<EduUserCourse> {

    Boolean check(String vid, String token);

    String add(String courseId,String userId);

    Boolean modifyStatus(String orderId);
}
