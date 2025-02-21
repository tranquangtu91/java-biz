package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.formfield.FormField;

public interface ICheckMoreThanMax {
    public Boolean checkMoreThanMax(FormField constraint, Map<String, Object> params);
}
