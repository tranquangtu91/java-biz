package com.base.admin.dto.exception;

import com.base.common.dto.general_response.GeneralResponse;
import com.base.common.dto.general_response.GeneralResponseErrorDetail;

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
