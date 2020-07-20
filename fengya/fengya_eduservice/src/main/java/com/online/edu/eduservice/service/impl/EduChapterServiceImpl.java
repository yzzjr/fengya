package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.entity.dto.EduChapterDto;
import com.online.edu.eduservice.entity.dto.EduVideoDto;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduChapterMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;


    @Override
    public void deleteChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<EduChapterDto> getChapterVideoListByCourseId(String courseId) {
        //根据课程id查询出章节部分
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        List<EduChapter> eduChapters =  baseMapper.selectList(wrapper);

        //根据课程id查询小姐
        QueryWrapper<EduVideo> wrapperVedio = new QueryWrapper<>();
        wrapperVedio.eq("course_id",courseId);

        List<EduVideo> eduVideos = eduVideoService.list(wrapperVedio);


        List<EduChapterDto> chapterDtos = new ArrayList<>();
        //便利课程下的章节，复制到dto中
        for (int i = 0; i <eduChapters.size() ; i++) {
            EduChapter chapter = eduChapters.get(i);
            EduChapterDto eduChapterDto = new EduChapterDto();
            BeanUtils.copyProperties(chapter,eduChapterDto);
            chapterDtos.add(eduChapterDto);
            List<EduVideoDto> eduVideoDtos = new ArrayList<>();
            for (int m = 0; m <eduVideos.size() ; m++) {
                EduVideo video = eduVideos.get(m);

                if(video.getChapterId().equals(chapter.getId()) ){
                    EduVideoDto eduVideoDto = new EduVideoDto();
                    BeanUtils.copyProperties(video,eduVideoDto);
                    eduVideoDtos.add(eduVideoDto);
                }
            }

            eduChapterDto.setChildren(eduVideoDtos);
        }
        return chapterDtos;
    }

    @Override
    public boolean removeChapterId(String chapterId) {
        //判断章节中是否有小姐
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);

        int count =  eduVideoService.count(wrapper);
        if(count>0){
            throw new EduException(20001,"删除失败");
        }
        else {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        }
    }
}
