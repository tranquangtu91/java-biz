package com.base.admin.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.base.admin.dto.exception.user.UserNotFoundException;
import com.base.admin.dto.exception.user.WrongPasswordException;
import com.base.common.dto.generalresponse.GeneralResponse;

import java.util.Map;

public interface IUserController {

    @PostMapping("/update-profile")
    ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> body)
            throws UserNotFoundException, WrongPasswordException;

    @GetMapping(path = { "/current-user", "/info" })
    ResponseEntity<GeneralResponse> currentUser();
}
