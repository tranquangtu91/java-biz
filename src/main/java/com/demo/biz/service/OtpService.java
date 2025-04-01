package com.demo.biz.service;

import com.demo.biz.dto.OtpResponseDto;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface OtpService {
    public OtpResponseDto registry(String issuer) throws IOException, WriterException;
    public boolean verifyOtp(String secretKey, int otp);
}