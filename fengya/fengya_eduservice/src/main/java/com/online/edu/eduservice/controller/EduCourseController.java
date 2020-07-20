package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.StatisClient;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.CourseQuery;
import com.online.edu.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-03
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private StatisClient statisClient;

    //1.添加课程信息
    @PostMapping()
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){

        String courseId =  eduCourseService.insertCourseInfo(courseInfoForm);
            return R.ok().data("courseId",courseId);

    }

    //根据课程id，查询课程信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getIdCourse(id);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    //修改课程
    @PostMapping("updateCourseInfo/{id}")
    public R updateCourseInfo(@PathVariable String id,@RequestBody CourseInfoForm courseInfoForm){
        Boolean flag = eduCourseService.updateCourse(courseInfoForm);
        if(flag){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("listCourse")
    public R getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("items",list);
    }

    @PostMapping("moreConditionPageList/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @RequestBody(required = false)
                    CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);

        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }


    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id){
        boolean flag = eduCourseService.removeCourse(id);
        if(flag ){
            return R.ok();
        }
        else {
            return R.error();
        }

    }

    //根据课程id获取课程星系信息
    @GetMapping("getAllCourseInfo/{courseId}")
    public R getAllCourseInfo(@PathVariable String courseId){
        CourseInfoDto courseInfoDto = eduCourseService.getCourseInfoAll(courseId);
        return R.ok().data("courseInfo",courseInfoDto);
    }

    @GetMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean result = eduCourseService.updateById(eduCourse);
        if(result ){
            statisClient.incCourseNum();
            return R.ok();
        }
        else {
            return R.error();
        }

    }

}

