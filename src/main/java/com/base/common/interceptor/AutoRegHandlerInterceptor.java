package com.base.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.base.common.configuration.WebMvcConfiguration;

public abstract class AutoRegHandlerInterceptor implements HandlerInterceptor {
    public AutoRegHandlerInterceptor() {
        super();
        WebMvcConfiguration.registryInterceptor(this);
    }
}
