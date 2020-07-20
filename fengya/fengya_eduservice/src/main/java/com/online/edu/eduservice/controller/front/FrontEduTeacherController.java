package com.online.edu.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontTeacher")
@CrossOrigin
public class FrontEduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    //讲师列表
    @GetMapping("{page}/{limit}")
    public R getFrontTeacherListPage(@PathVariable Long page,
                                     @PathVariable Long limit){

        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        Map<String,Object> map = eduTeacherService.getFrontTeacher(pageTeacher);

        return R.ok().data(map);

    }

    @GetMapping("{id}")
    public R getTeacherInfoCourseId(@PathVariable String id){
        //1.根据讲师id查询讲师详情信息
        EduTeacher eduTeacher = eduTeacherService.getById(id);

        //2.根据讲师id查询所讲课程，返回list
        List<EduCourse> courseList = eduTeacherService.getCourseListByTeacherId(id);
        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);

    }

}
