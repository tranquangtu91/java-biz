package com.base.admin.application.service.user;

import java.util.Map;

import com.base.admin.domain.dto.exception.user.UserNotFoundException;
import com.base.admin.domain.dto.exception.user.WrongPasswordException;
import com.base.admin.domain.entity.user.User;

public interface IUserService {
    

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
