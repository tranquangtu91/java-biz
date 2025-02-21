package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.form_field.FormField;

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
