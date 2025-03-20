package com.base.admin.application.service.user;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthService {
    /**
     * Đăng nhập
     * 
     * @param username
     * @param password
     * @return
     */
    public Map<String, Object> login(String username, String password);

    /**
     * Refresh token
     * 
     * @param refreshToken
     * @param userDetails
     * @return
     */
    public Map<String, Object> refreshToken(String refreshToken, UserDetails userDetails);

    /**
     * Đăng xuất
     * 
     * @param accessToken
     * @param userDetails
     */
    public void logout(String accessToken, UserDetails userDetails);
}
