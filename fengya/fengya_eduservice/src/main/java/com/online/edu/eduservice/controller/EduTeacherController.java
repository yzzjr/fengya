package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import com.sun.org.apache.xalan.internal.lib.ExsltDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhouzhou
 * @since 2020-03-12
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //分页查询讲师列表
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long  page,@PathVariable Long limit){
        //创建page对象，传递两个参数
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        eduTeacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("items",records);


    }

    @PostMapping("login")
    public  R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


    @GetMapping
    public R getAllTeacherList(){
        //注入service
        List<EduTeacher>eduTeachers = eduTeacherService.list(null);

        return R.ok().data("items",eduTeachers);
    }

    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable String id){
        boolean result = eduTeacherService.removeById(id);
        if(result){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    //多条件组合查询带分页
    @PostMapping("moreCondtionPageList/{page}/{limit}")
    public R getMoreCondtionPageList(@PathVariable Long page, @PathVariable Long limit,
                                     @RequestBody(required = false) QueryTeacher queryTeacher){
        Page<EduTeacher> pageTeacher  = new Page<>(page,limit);

        eduTeacherService.pageListCondition(pageTeacher,queryTeacher);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("items",records);

    }

    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    @PostMapping("updateTeacher")

    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);

        if(b){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

}

