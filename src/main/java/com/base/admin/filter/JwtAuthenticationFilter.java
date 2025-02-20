package com.base.admin.filter;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.admin.utils.auth_provider.JwtProvider;
import com.base.common.configuration.SecurityFilterChainConfiguration;
import com.base.common.dto.user.UserDetailsImpl;
import com.base.common.utils.Closure;
import com.base.common.utils.cache.CacheHandlerFactory;
import com.base.common.utils.cache.ICacheHandler;
import com.base.common.utils.convert.string.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    static Long JWT_CACHE_TTL = 30l;

    JwtProvider jwtProvider;
    Boolean verbose = false;

    ICacheHandler cacheHandler;

    JwtAuthenticationFilter(@Autowired JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;

        SecurityFilterChainConfiguration.registerFilter(this);

        cacheHandler = CacheHandlerFactory.getCacheHandler();
    }

    @SuppressWarnings({ "unchecked", "null" })
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                String key = String.format("login:session:jwt:%s", jwt);
                UserDetailsImpl userDetail = (UserDetailsImpl) cacheHandler.memoize(key, JWT_CACHE_TTL, new Closure() {

                    @Override
                    public Object handler(Object... args) {
                        log.debug(String.format("calc jwt '%s'...", StringUtils.maskingData(jwt, 16, -16)));
                        return jwtProvider.getUserDetail(jwt);
                    }

                });

                if (userDetail != null) {
                    List<SimpleGrantedAuthority> grantedAuthorities = (List<SimpleGrantedAuthority>) userDetail.authorities;
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetail, null, grantedAuthorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            log.error(String.format("%s...", ex.getClass().getName()));
            if (verbose) {
                ex.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }

    String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
