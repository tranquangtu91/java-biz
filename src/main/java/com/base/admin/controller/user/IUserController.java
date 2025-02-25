package com.base.admin.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.base.admin.dto.exception.user.UserNotFoundException;
import com.base.admin.dto.exception.user.WrongPasswordException;
import com.base.common.dto.generalresponse.GeneralResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface IUserController {

    /**
     * 
     * @param body
     * @return
     * @throws UserNotFoundException
     * @throws WrongPasswordException
     */
    @PutMapping("/update-profile")
    ResponseEntity<?> updateProfile(HttpServletRequest request, @RequestBody Map<String, Object> body)
            throws UserNotFoundException, WrongPasswordException;

    /**
     * 
     * @return
     */
    @GetMapping(path = { "/current-user", "/info" })
    ResponseEntity<GeneralResponse> currentUser();

    /**
     * 
     * @return
     */
    @GetMapping(path = "/menu")
    ResponseEntity<GeneralResponse> menu();
}
