package com.demo.biz.controller;
import com.demo.biz.dto.OtpRequestDto;
import com.demo.biz.dto.OtpResponseDto;
import com.demo.biz.entity.VerifyOtpEntity;
import com.demo.biz.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp/auth")
public class OtpController {
    private final OtpService otpService;
    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }
    // API để đăng ký user và lấy QR code + secretKey
    @PostMapping("/register")
    public ResponseEntity<OtpResponseDto> registerUser(@RequestBody OtpRequestDto request) {
        try {
            OtpResponseDto otpResponseDto  = otpService.registry(request.getIssuer());
            return ResponseEntity.ok(otpResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OtpResponseDto());
        }
    }
    @GetMapping("/register/{userName}")
    public ResponseEntity<OtpResponseDto> getQRCode(@PathVariable String userName) {
        try {
            OtpResponseDto otpResponseDto  = otpService.registry(userName);
            return ResponseEntity.ok(otpResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OtpResponseDto());
        }
    }
    // API để xác thực OTP
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyOtp(@RequestBody OtpRequestDto otpRequest, HttpServletRequest httpRequest) {
        boolean isValid = otpService.verifyOtp(otpRequest.getSecretKey(), otpRequest.getOtp());
        if(isValid){
            String authorization = httpRequest.getHeader("Authorization");
            String accessToken = authorization.substring(7);
            VerifyOtpEntity.setPassVerifyOtp(accessToken, true);
        }
        return ResponseEntity.ok(isValid);
    }
}
