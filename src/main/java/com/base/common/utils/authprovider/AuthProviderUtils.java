package com.base.common.utils.authprovider;

import java.util.HashMap;
import java.util.Map;

import com.base.common.dto.user.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthProviderUtils {
    public static Map<String, IAuthProvider> authProviders = new HashMap<>();

    public static void regProvider(IAuthProvider provider) {
        log.info(String.format("register new authen provider '%s'...", provider.getClass().getName()));
        AuthProviderUtils.authProviders.put(provider.getClass().getName(), provider);
    }

    public static UserDetailsImpl getUserDetail(String accessToken) {
        for (IAuthProvider it : authProviders.values()) {
            UserDetailsImpl userDetail = it.getUserDetail(accessToken);
            if (userDetail != null) {
                return userDetail;
            }
        }

        return new UserDetailsImpl() {
            {
                username = "anonymousUser";
            }
        };
    }
}
