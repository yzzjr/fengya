package com.online.edu.vidservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VidService {
    String uploadVideoAliyun(MultipartFile file);

    void deleteAliyunVideoId(String videoId);

    void deleteVideoMore(List videoList);

    Integer check(String vid, String token);
}
