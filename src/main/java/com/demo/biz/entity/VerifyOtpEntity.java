package com.demo.biz.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VerifyOtpEntity {
    private static Map<String, Boolean> userOtpStatus = new ConcurrentHashMap<>();

    public static boolean isPassVerifyOtp(String key) {
        return userOtpStatus.getOrDefault(key, false);
    }

    public static void setPassVerifyOtp(String key, boolean value) {
        userOtpStatus.put(key, value);
    }

    public static void clearPassVerifyOtp(String key) {
        userOtpStatus.remove(key);
    }

}
