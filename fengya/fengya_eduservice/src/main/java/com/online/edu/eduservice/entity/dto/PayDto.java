package com.online.edu.eduservice.entity.dto;

import lombok.Data;

@Data
public class PayDto {

    Integer amount;
    String courseId;
    String storeName;
    String token;

}
