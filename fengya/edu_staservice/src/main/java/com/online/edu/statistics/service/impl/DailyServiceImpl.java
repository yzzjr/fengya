package com.online.edu.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.common.R;
import com.online.edu.statistics.UcenterClient;
import com.online.edu.statistics.entity.Daily;
import com.online.edu.statistics.mapper.DailyMapper;
import com.online.edu.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-10
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    private UcenterClient ucenterClient;


    @Override
    public void getCountRegisterNum(String day) {
        //判断是否有数据，若有则删除
//        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
//        wrapper.eq("date_calculated",day);
//        baseMapper.delete(wrapper);


//        R r = ucenterClient.countRegisterNum(day);
//        Integer registerCount = (Integer) r.getData().get("registerCount");
//        Integer courseNum = RandomUtils.nextInt(100,200);//TODO
//        Integer login = RandomUtils.nextInt(100,200);//TODO
//        Integer videoViewNum = RandomUtils.nextInt(100,200);//TODO
//
//        Daily daily = new Daily();
//        daily.setDateCalculated(day);
//        daily.setRegisterNum(registerCount);
//        daily.setCourseNum(courseNum);
//        daily.setLoginNum(login);
//        daily.setVideoViewNum(videoViewNum);
//        baseMapper.insert(daily);


    }

    @Override
    public Map<String, Object> getDataCount(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
//        wrapper.ge("date_calculated",begin);
//        wrapper.le("date_calculated",end);
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<Daily> dailyList = baseMapper.selectList(wrapper);
        List<Integer> dataList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < dailyList.size(); i++) {
            Daily daily = dailyList.get(i);
            String dateCalculated = daily.getDateCalculated();
            timeList.add(dateCalculated);

            switch (type) {
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("timeList",timeList);
        map.put("dataList",dataList);


        return map;
    }

    @Override
    public Boolean addRegister() {
        Date date = new Date();
        Daily daily = new Daily();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();

        wrapper.eq("date_calculated",today);

        if(baseMapper.selectCount(wrapper) != 0){
            daily = baseMapper.selectOne(wrapper);
            daily.setRegisterNum(daily.getRegisterNum()+1);
            int i = baseMapper.updateById(daily);
            return i>0;
        }
        else {
            daily.setCourseNum(0);
            daily.setLoginNum(0);
            daily.setRegisterNum(1);
            daily.setVideoViewNum(0);
            daily.setDateCalculated(today);
            int insert = baseMapper.insert(daily);
            return insert>0;
        }
    }

    @Override
    public Boolean addLogin() {
        Date date = new Date();
        Daily daily = new Daily();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();

        wrapper.eq("date_calculated",today);

        if(baseMapper.selectCount(wrapper) != 0){
            daily = baseMapper.selectOne(wrapper);
            daily.setLoginNum(daily.getLoginNum()+1);
            int i = baseMapper.updateById(daily);
            return i>0;
        }
        else {
            daily.setCourseNum(0);
            daily.setLoginNum(1);
            daily.setRegisterNum(0);
            daily.setVideoViewNum(0);
            daily.setDateCalculated(today);
            int insert = baseMapper.insert(daily);
            return insert>0;
        }
    }

    @Override
    public Boolean addVideo() {
        Date date = new Date();
        Daily daily = new Daily();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();

        wrapper.eq("date_calculated",today);

        if(baseMapper.selectCount(wrapper) != 0){
            daily = baseMapper.selectOne(wrapper);
            daily.setVideoViewNum(daily.getVideoViewNum()+1);
            int i = baseMapper.updateById(daily);
            return i>0;
        }
        else {
            daily.setCourseNum(0);
            daily.setLoginNum(0);
            daily.setRegisterNum(0);
            daily.setVideoViewNum(1);
            daily.setDateCalculated(today);
            int insert = baseMapper.insert(daily);
            return insert>0;
        }
    }

    @Override
    public Boolean addCourse() {
        Date date = new Date();
        Daily daily = new Daily();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();

        wrapper.eq("date_calculated",today);

        if(baseMapper.selectCount(wrapper) != 0){
            daily = baseMapper.selectOne(wrapper);
            daily.setCourseNum(daily.getCourseNum()+1);
            int i = baseMapper.updateById(daily);
            return i>0;
        }
        else {
            daily.setVideoViewNum(0);
            daily.setLoginNum(0);
            daily.setRegisterNum(0);
            daily.setCourseNum(1);
            daily.setDateCalculated(today);
            int insert = baseMapper.insert(daily);
            return insert>0;
        }
    }

//    public void test(){
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String today = dateFormat.format(date);
//        System.out.println(today);
//    }

//    public static void main(String[] args) {
//        DailyServiceImpl dailyService =new DailyServiceImpl();
//        dailyService.getCountRegisterNum("2019-01-19");
//    }
}
