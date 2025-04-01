package com.demo.biz.dto;

import lombok.Data;

@Data
public class OtpRequestDto {
    private String issuer;
    private String secretKey;
    private int otp;
}
