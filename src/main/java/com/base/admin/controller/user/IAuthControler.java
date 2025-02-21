package com.base.admin.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.base.admin.dto.user.LoginRequestDto;
import com.base.admin.dto.user.RefreshTokenRequestDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IAuthControler {
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequestDto body);

    @GetMapping("/logout")
    ResponseEntity<?> logout(HttpServletRequest request);

    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto body);
}
