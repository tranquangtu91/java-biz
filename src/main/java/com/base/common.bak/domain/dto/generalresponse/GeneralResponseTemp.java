package com.base.common.domain.dto.generalresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponseTemp {
    public String errorCode;
    public String message;
    public ResponseCode code;
}
