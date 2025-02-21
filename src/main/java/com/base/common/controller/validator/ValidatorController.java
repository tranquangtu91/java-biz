package com.base.common.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.dto.formfield.FormField;
import com.base.common.dto.generalresponse.GeneralResponse;
import com.base.common.utils.validator.ValidatorUtils;

@RestController()
@RequestMapping(path = "/api")
public class ValidatorController {

    @Autowired
    ValidatorUtils validator;

    @PostMapping(path = { "/v1/common-module/validator/validate" })
    public Object validate(@RequestBody() Map<String, Object> body) {
        List<FormField> constraints = new ArrayList<FormField>();
        FormField formField = new FormField();
        formField.code = "key1";
        formField.required = true;
        formField.pattern = "^\\w+$";
        formField.minLength = 6;
        constraints.add(formField);
        GeneralResponse gr = validator.validate(constraints, body);
        return gr;
    }
}
