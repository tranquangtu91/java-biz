package com.base.admin.infrastructure.interceptor.requestmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.base.admin.application.port.out.repository.requestmap.RequestMapRepository;
import com.base.admin.domain.dto.requestmap.RequestMapDto;
import com.base.admin.domain.entity.requestmap.RequestMap;
import com.base.common.application.utils.convert.object.ObjectUtils;
import com.base.common.application.utils.regex.RegexUtils;
import com.base.common.domain.dto.user.UserDetailsImpl;
import com.base.common.infrastructure.interceptor.AutoRegHandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestMapInterceptor extends AutoRegHandlerInterceptor {
    static List<RequestMapDto> requestMaps = new ArrayList<>();

    @Autowired
    static RequestMapRepository requestMapRepository;

    RequestMapInterceptor(@Autowired RequestMapRepository requestMapRepository) {
        super();

        RequestMapInterceptor.requestMapRepository = requestMapRepository;
        clearCachedRequestMaps();
    }

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpStatus httpStatus = hasAccess(request);
        if (httpStatus != HttpStatus.OK) {
            response.setStatus(httpStatus.value());
            return false;
        }
        return true;
    }

    HttpStatus hasAccess(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = new ArrayList<>();
        String username = "anonymousUser";

        if (authentication != null) {
            authorities.addAll(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            username = ((UserDetailsImpl) (authentication.getPrincipal())).getUsername();
        }
        if (authorities.size() > 0) {
            authorities.add("authenticated");
        }
        authorities.add("permitAll");

        boolean hasAccess = requestMaps.size() == 0;
        Long requestMapId = -1l;
        for (RequestMapDto it : requestMaps) {
            Matcher matcher = it.pattern.matcher(request.getRequestURI());
            if (matcher.matches() && (ObjectUtils.isEmpty(it.requestMap.getHttpMethod())
                    || it.requestMap.getHttpMethod().equals(request.getMethod()))) {
                boolean __hasAccess = false;
                if (it.configAttributes.contains("denyAll")) {
                    hasAccess = __hasAccess;
                    continue;
                }
                for (String authority : authorities) {
                    __hasAccess |= it.configAttributes.contains(authority);
                    if (__hasAccess) {
                        requestMapId = it.requestMap.getId();
                        break;
                    }
                }
                hasAccess = __hasAccess;
            }
        }

        HttpStatus httpStatus = HttpStatus.OK;
        if (!hasAccess) {
            if (authentication == null)
                httpStatus = HttpStatus.UNAUTHORIZED;
            else
                httpStatus = HttpStatus.FORBIDDEN;

        }

        log.info(String.format("[%s][%s][%s][%s] %s", httpStatus, request.getMethod(), username, requestMapId,
                request.getRequestURI()));
        return httpStatus;
    }

    public static void clearCachedRequestMaps() {
        requestMaps.clear();

        List<RequestMap> __requestMaps = RequestMapInterceptor.requestMapRepository.findAllByActive(true);
        RequestMapInterceptor.requestMaps = __requestMaps.stream().map(it -> {
            RequestMapDto requestMapDto = new RequestMapDto();
            requestMapDto.configAttributes = Arrays.asList(it.getConfigAttributes().split(","));
            requestMapDto.pattern = globToRegExp(it.getUrl());
            requestMapDto.requestMap = it;
            return requestMapDto;
        }).collect(Collectors.toList());

    }

    static Pattern globToRegExp(String glob) {
        return RegexUtils.globToRegExp(glob);
    }
}
