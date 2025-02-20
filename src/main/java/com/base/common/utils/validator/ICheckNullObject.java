package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.form_field.FormField;

public interface ICheckNullObject {
    
    /**
     * Kiểm tra lỗi NULL_OBJECT
     * 
     * @param constraint
     * @param params
     * @returns
     */
    public Boolean checkNullObject(FormField constraint, Map<String, Object> params);
}
