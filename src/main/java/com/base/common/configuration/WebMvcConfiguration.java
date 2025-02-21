package com.base.common.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    static InterceptorRegistry registry;
    static List<HandlerInterceptor> interceptorHandlers = new ArrayList<>();

    public static void registryInterceptor(HandlerInterceptor handler) {
        log.info(String.format("register interceptor '%s'", handler.getClass().getName()));

        if (WebMvcConfiguration.registry != null) {
            WebMvcConfiguration.registry.addInterceptor(handler);
        } else {
            interceptorHandlers.add(handler);
        }
    }

    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        WebMvcConfiguration.registry = registry;
        interceptorHandlers.forEach(it -> {
            WebMvcConfiguration.registry.addInterceptor(it);
        });
    }
}
