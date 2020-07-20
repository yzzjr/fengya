package com.online.edu.eduservice.mapper;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.eduservice.entity.dto.CourseAllInfoDto;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-03
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseInfoDto getCourseInfoAll(String courseId);

    CourseAllInfoDto getCourseAllInfo(String courseTd);



}
