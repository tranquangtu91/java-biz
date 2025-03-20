package com.base.common.domain.dto.datatablefilter;

import java.util.List;

import com.base.common.domain.dto.user.UserDetailsImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadDataTableOptions {
    /**
     * Các trường cần trả ra
     */
    public List<String> fields;

    public UserDetailsImpl userDetail;

    public Class<?> domainClass;
}
