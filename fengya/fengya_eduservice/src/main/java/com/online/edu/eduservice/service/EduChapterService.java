package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.EduChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-05
 */
public interface EduChapterService extends IService<EduChapter> {
    //根据课程id删除章节
    void deleteChapterByCourseId(String id);

    List<EduChapterDto> getChapterVideoListByCourseId(String courseId);

    boolean removeChapterId(String chapterId);
}
