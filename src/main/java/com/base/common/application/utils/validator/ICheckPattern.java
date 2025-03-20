package com.base.common.application.utils.validator;

import java.util.Map;

import com.base.common.domain.dto.formfield.FormField;

public interface ICheckPattern {
    public Boolean checkPattern(FormField constraint, Map<String, Object> params);
}
