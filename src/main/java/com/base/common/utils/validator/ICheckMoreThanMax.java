package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.form_field.FormField;

public interface ICheckMoreThanMax {
    public Boolean checkMoreThanMax(FormField constraint, Map<String, Object> params);
}
