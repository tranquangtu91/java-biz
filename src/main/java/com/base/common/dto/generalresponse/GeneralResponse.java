package com.base.common.dto.generalresponse;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse {
    public ResponseCode code = ResponseCode.SUCCESS;
    private long transactionTime = System.currentTimeMillis();

    public Object value;

    public String errorCode;
    public String category;
    public String subCategory;
    public String message;
    public String messageDetail;

    /**
     * Tạo đối tượng General Response từ GeneralResponseTemp
     * 
     * @param errorCodeDetail
     * @param options
     * @return
     */
    public static GeneralResponse getInstance(GeneralResponseTemp errorCodeDetail,
            @Nullable GeneralResponseGetInstanceOptions options) {
        GeneralResponse __gr = new GeneralResponse();
        __gr.code = errorCodeDetail.code;
        __gr.errorCode = errorCodeDetail.errorCode;
        __gr.message = (options != null && options.message != null) ? options.message : errorCodeDetail.message;
        __gr.value = (options != null && options.value != null) ? options.value : null;
        return __gr;
    }

    public static GeneralResponse success(Object value) {
        GeneralResponse __gr = new GeneralResponse();
        __gr.code = ResponseCode.SUCCESS;
        __gr.message = "Success";
        __gr.value = value;
        return __gr;
    }

    public static GeneralResponse getInstance(GeneralResponseTemp errorCodeDetail) {
        return getInstance(errorCodeDetail, null);
    }

    public Integer getCode() {
        return this.code.value;
    }
}
