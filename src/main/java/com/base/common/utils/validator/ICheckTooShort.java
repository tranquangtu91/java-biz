package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.form_field.FormField;

public interface ICheckTooShort {
    /**
     * Kiểm tra lỗi TOO_SHORT
     * 
     * @param constraint
     * @param params
     * @returns
     */
    public Boolean checkTooShort(FormField constraint, Map<String, Object> params);
}
