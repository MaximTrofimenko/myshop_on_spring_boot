package com.trofimenko.myshop.persistence.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPojo {

    private String commentary;

    private String captchaCode;
    private UUID productId;
}
