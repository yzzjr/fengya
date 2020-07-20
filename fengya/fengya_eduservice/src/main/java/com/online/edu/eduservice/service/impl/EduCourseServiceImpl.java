package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.dto.CourseAllInfoDto;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.CourseQuery;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //添加课程信息
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;


    @Override
    public String insertCourseInfo(CourseInfoForm courseInfoForm) {
        //添加课程基本信息到基本表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.insert(eduCourse);
        if(result ==0){
            throw  new EduException(20001,"添加课程失败");
        }
        EduCourseDescription eduCourseDescription =new EduCourseDescription();
        String description = courseInfoForm.getDescription();
        eduCourseDescription.setDescription(description);

        String courseId = eduCourse.getId();
        eduCourseDescription.setId(courseId);
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if(save){
            return courseId;
        }
        else {return null;}


        //添加课程描述到描述表中


    }

    @Override
    public CourseInfoForm getIdCourse(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new EduException(20001,"无课程信息");

        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);


        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        String description = eduCourseDescription.getDescription();

        courseInfoForm.setDescription(description);
        return courseInfoForm;
    }

    @Override
    public Boolean updateCourse(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result == 0){
            throw new EduException(20001,"修改分类失败");
        }
        String id = courseInfoForm.getId();
        String description = courseInfoForm.getDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(description);
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);

        return b;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();


        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }


        baseMapper.selectPage(pageParam, queryWrapper);

    }

    @Override
    public boolean removeCourse(String id) {
        eduChapterService.deleteChapterByCourseId(id);
        eduVideoService.deleteVideoByCourseId(id);
        eduCourseDescriptionService.deleteDescriptionByCourseId(id);
        int result = baseMapper.deleteById(id);
        return result>0;
    }

    @Override
    public CourseInfoDto getCourseInfoAll(String courseId) {

        CourseInfoDto courseInfoAll = baseMapper.getCourseInfoAll(courseId);


        return courseInfoAll;
    }

    @Override
    public Map<String, Object> listCoursePage(Page<EduCourse> pageCourse) {

        baseMapper.selectPage(pageCourse,null);

        //从basemapper中获取分页数据放到map中去
        List<EduCourse> records = pageCourse.getRecords();
        long total = pageCourse.getTotal();
        long size = pageCourse.getSize();
        long pages = pageCourse.getPages();
        long current = pageCourse.getCurrent();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseAllInfoDto getCourseAllInfo(String courseId) {


        return baseMapper.getCourseAllInfo(courseId);
    }
}
