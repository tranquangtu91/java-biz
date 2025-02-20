package com.base.admin.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.base.admin.dto.exception.user.UserNotFoundException;
import com.base.admin.dto.exception.user.WrongPasswordException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IUserController {
    @PostMapping("/login")
    ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> body);

    @GetMapping("/logout")
    ResponseEntity<?> logout();

    @GetMapping("/info")
    ResponseEntity<?> info(HttpServletRequest request, HttpServletResponse response);

    @PostMapping("/update-profile")
    ResponseEntity<?> updateProfile(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> body) throws UserNotFoundException, WrongPasswordException;
}
