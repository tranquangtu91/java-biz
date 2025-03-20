package com.base.common.application.utils.validator;

import java.util.Map;

import com.base.common.domain.dto.formfield.FormField;

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
