package com.base.common.infrastructure.adapter.in.rest.file;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.application.utils.convert.object.ObjectUtils;
import com.base.common.application.utils.file.Base64FileUtils;
import com.base.common.application.utils.validator.ValidatorUtils;
import com.base.common.domain.dto.formfield.FormField;
import com.base.common.domain.dto.generalresponse.GeneralResponse;
import com.base.common.domain.dto.generalresponse.ResponseCode;

@RestController()
@RequestMapping(path = "/api")
public class FileController {

    @Autowired
    ValidatorUtils validatorUtils;

    FileController() {
        ValidatorUtils.registryConstraint("/v1/common-module/file/download", new ArrayList<FormField>() {
            {
                add(new FormField() {
                    {
                        code = "filePath";
                        required = true;
                    }
                });
            }
        });
    }

    @PostMapping(path = { "/v1/common-module/file/download" })
    public Object validate(@RequestBody() Map<String, Object> body) {
        GeneralResponse gr = validatorUtils.validate("/v1/common-module/file/download", body);
        if (gr.code == ResponseCode.ERROR)
            return ObjectUtils.modifyData(gr, null);
            
        gr = Base64FileUtils.toBase64(body.get("filePath").toString());
        return ObjectUtils.modifyData(gr, null);
    }
}
