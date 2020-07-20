package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.common.R;
import com.online.edu.eduservice.UcenterService;
import com.online.edu.eduservice.entity.EduUserCourse;
import com.online.edu.eduservice.mapper.EduUserCourseMapper;
import com.online.edu.eduservice.service.EduUserCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-06-07
 */
@Service
public class EduUserCourseServiceImpl extends ServiceImpl<EduUserCourseMapper, EduUserCourse> implements EduUserCourseService {

    @Autowired
    UcenterService ucenterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public Boolean check(String vid, String token) {
        String courseId = eduVideoService.getCourseByVid(vid);
        R r = ucenterService.getIdByToken(token);
        String userId=(String)r.getData().get("uid");

        QueryWrapper<EduUserCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",userId);
        wrapper.eq("cid",courseId);
        if(baseMapper.selectCount(wrapper)!=0){
            wrapper.eq("status",1);
            if(baseMapper.selectCount(wrapper) == 1){
                return true;
            }
            else {return false;}
        }
        else {return false;}
    }

    @Override
    public String add(String courseId, String userId) {

        String orderId = UUID.randomUUID().toString();
        EduUserCourse eduUserCourse = new EduUserCourse();
        eduUserCourse.setCid(courseId);
        eduUserCourse.setUid(userId);
        eduUserCourse.setOrderid(orderId);
        eduUserCourse.setStatus(0);
        baseMapper.insert(eduUserCourse);

        return  orderId;
    }

    @Override
    public Boolean modifyStatus(String orderId) {
        QueryWrapper<EduUserCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("orderid",orderId);

        EduUserCourse eduUserCourse = baseMapper.selectOne(wrapper);
        eduUserCourse.setStatus(1);
        int i = baseMapper.update(eduUserCourse,wrapper);

        return i>0;
    }
}
