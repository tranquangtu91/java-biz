package com.base.admin.domain.dto.exception;

import com.base.common.domain.dto.generalresponse.GeneralResponse;
import com.base.common.domain.dto.generalresponse.GeneralResponseErrorDetail;

public class CustomizeException extends Exception {

    public CustomizeException(String message) {
        super(message);
    }

    public GeneralResponse toGeneralResponse() {
        GeneralResponse gr = GeneralResponse.getInstance(GeneralResponseErrorDetail.INTERNAL_SERVER_ERROR);
        gr.message = getMessage();
        return gr;
    }

}
