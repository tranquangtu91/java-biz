package com.demo.biz.interceptor;

import com.base.common.infrastructure.interceptor.AutoRegHandlerInterceptor;
import com.demo.biz.entity.VerifyOtpEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OTPInterceptor extends AutoRegHandlerInterceptor {

    public OTPInterceptor() {
        super();
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy đường dẫn URI từ request
        String requestURI = request.getRequestURI();
        // Bypass Interceptor
        if (requestURI.contains("/verify") || requestURI.contains("/login") || requestURI.contains("/logout") || requestURI.contains("/register")) {
            return true; // Bỏ qua kiểm tra isPassVerifyOtp
        }
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.substring(7);
        if (accessToken != null) {
            boolean isPassVerifyOtp = VerifyOtpEntity.isPassVerifyOtp(accessToken);
            if (!isPassVerifyOtp) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }
}
