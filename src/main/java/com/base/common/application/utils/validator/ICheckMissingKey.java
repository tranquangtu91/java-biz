package com.base.common.application.utils.validator;

import java.util.Map;

import com.base.common.domain.dto.formfield.FormField;

public interface ICheckMissingKey {
    /**
     * Kiểm tra lỗi thiếu MISSING_KEY
     * 
     * @param constraint
     * @param params
     * @returns
     */
    public Boolean checkMissingKey(FormField constraint, Map<String, Object> params);
}
