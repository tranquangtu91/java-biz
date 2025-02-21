package com.base.common.utils.authprovider;

import java.util.Map;

import com.base.common.dto.user.UserDetailsImpl;

public interface IAuthProvider {
    
    String createAccessToken(UserDetailsImpl userDetail);

    Map<String, Object> verifyAccessToken(String accessToken);

    UserDetailsImpl getUserDetail(String accessToken);

    Map<String, Object> createResponsePayload(UserDetailsImpl userDetail);
}
