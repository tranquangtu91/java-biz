package com.base.admin.service.user;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.base.admin.dto.exception.user.UserNotFoundException;
import com.base.admin.dto.exception.user.WrongPasswordException;
import com.base.admin.entity.user.User;

public interface IUserService {
    /**
     * Đăng nhập
     * 
     * @param username
     * @param password
     * @return
     */
    public Map<String, Object> login(String username, String password);

    /**
     * Đăng xuất
     * 
     * @param accessToken
     * @param userDetail
     */
    public void logout(String accessToken, UserDetails userDetail);

    /**
     * Tìm kiếm user theo username
     * 
     * @param username
     * @return
     */
    public User findByUsername(String username);
    
    /**
     * Cập nhật thông tin user
     * 
     * @param username
     * @param item
     * @throws UserNotFoundException
     * @throws WrongPasswordException
     */
    public User updateProfile(String username, Map<String, Object> item) throws UserNotFoundException, WrongPasswordException;
}
