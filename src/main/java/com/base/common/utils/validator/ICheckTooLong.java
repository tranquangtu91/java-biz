package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.form_field.FormField;

public interface ICheckTooLong {
    /**
     * Kiểm tra lỗi TOO_LONG
     * 
     * @param constraint
     * @param params
     * @returns
     */
    public Boolean checkTooLong(FormField constraint, Map<String, Object> params);
}
