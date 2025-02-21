package com.base.common.service;

import java.util.List;

import com.base.common.dto.user.UserDetailsImpl;

public class LoadDataTableOptions {
    /**
     * Các trường cần trả ra
     */
    public List<String> fields;

    public UserDetailsImpl userDetail;

    public Class<?> domainClass;
}
