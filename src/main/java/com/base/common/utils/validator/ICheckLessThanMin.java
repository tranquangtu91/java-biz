package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.form_field.FormField;

public interface ICheckLessThanMin {
    Boolean checkLessThanMin(FormField constraint, Map<String, Object> params);
}
