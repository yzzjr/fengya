package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.query.QueryTeacher;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-03-12
 */
public interface EduTeacherService extends IService<EduTeacher> {


    void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    Map<String,Object> getFrontTeacher(Page<EduTeacher> pageTeacher);

    List<EduCourse> getCourseListByTeacherId(String s);
}
