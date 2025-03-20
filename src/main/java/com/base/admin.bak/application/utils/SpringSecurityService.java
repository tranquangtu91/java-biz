package com.base.admin.application.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.base.admin.infrastructure.interceptor.requestmap.RequestMapInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SpringSecurityService {
    
    public static UserDetails getPrincipal() {
        UserDetails userDetails = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (UserDetails) authentication.getPrincipal();
        } else {

        }

        return userDetails;
    }

    public static void clearCacheRequestMap() {
        log.info("clear cache request map...");
        RequestMapInterceptor.clearCachedRequestMaps();
        log.info("clear cache request map done");
    }
}
