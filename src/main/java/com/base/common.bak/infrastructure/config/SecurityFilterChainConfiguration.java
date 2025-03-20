package com.base.common.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityFilterChainConfiguration implements SecurityFilterChain {

    public static List<Filter> filters = new ArrayList<>();

    public static void registerFilter(Filter filter) {
        registerFilter(filter, null);
    }

    public static void registerFilter(Filter filter, Integer order) {
        if (order == null)
            order = -1;
        log.info(String.format("register filter [%d] '%s'...", order, filter.getClass().getName()));
        filters.add(filter);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }

    @Override
    public List<Filter> getFilters() {
        return filters;
    }

}
