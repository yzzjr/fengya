package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    //读取excel内容
    List<String> importSubject(MultipartFile file);

    List<OneSubjectDto> getSubjectList();

    boolean deleteSubjectById(String id);

    boolean saveOneLevel(EduSubject eduSubject);

    boolean saveTwoLevel(EduSubject eduSubject);
}
