package com.base.common.infrastructure.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.base.common.infrastructure.config.WebMvcConfiguration;

public abstract class AutoRegHandlerInterceptor implements HandlerInterceptor {
    public AutoRegHandlerInterceptor() {
        super();
        WebMvcConfiguration.registryInterceptor(this);
    }
}
