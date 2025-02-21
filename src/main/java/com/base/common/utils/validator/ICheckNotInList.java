package com.base.common.utils.validator;

import java.util.Map;

import com.base.common.dto.formfield.FormField;

public interface ICheckNotInList {
    public Boolean checkNotInList(FormField constraint, Map<String, Object> params);
}
