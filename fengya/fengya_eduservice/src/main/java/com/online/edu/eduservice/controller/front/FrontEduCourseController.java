package com.online.edu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.dto.CourseAllInfoDto;
import com.online.edu.eduservice.entity.dto.EduChapterDto;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontCourse")
@CrossOrigin
public class FrontEduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("{page}/{limit}")
    public R getCourseListPage(@PathVariable Long page,
                               @PathVariable Long limit){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = eduCourseService.listCoursePage(pageCourse);
        return R.ok().data(map);

    }

    @GetMapping("{id}")
    public R getCourseInfoAll(@PathVariable String id){
        CourseAllInfoDto courseAllInfoDto = eduCourseService.getCourseAllInfo(id);

        List<EduChapterDto> chapterVideoList = eduChapterService.getChapterVideoListByCourseId(id);


        return R.ok().data("courseInfo",courseAllInfoDto).data("chapterVideoList",chapterVideoList);

    }



}
