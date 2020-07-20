package com.online.edu.eduservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface PayingService {

    public String trade_precreate(String orderId,Integer Amount,String courseId,String storeName,String userId);

    public String createOrder(String courseId,String userId);

    public Boolean getOrderStatus(String orderId);



}
