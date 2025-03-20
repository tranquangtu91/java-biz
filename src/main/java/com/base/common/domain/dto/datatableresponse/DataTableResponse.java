package com.base.common.domain.dto.datatableresponse;

import java.util.ArrayList;
import java.util.List;

import com.base.common.domain.dto.generalresponse.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTableResponse<T> {
    public ResponseCode code = ResponseCode.SUCCESS;
    Long transactionTime = System.currentTimeMillis();

    public String errorCode;
    public String category;
    public String subCategory;
    public String message;
    public String messageDetail;

    public Long totalRows = 0L;
    public Integer rows = 0;
    public Integer first = 0;
    public List<T> items = new ArrayList<>();

    public Integer getCode() {
        return this.code.value;
    }
}
