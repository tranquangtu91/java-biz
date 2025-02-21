package com.base.common.utils.validator;

import com.base.common.dto.generalresponse.GeneralResponseTemp;
import com.base.common.dto.generalresponse.ResponseCode;

public class ConstraintError {
    public static final GeneralResponseTemp NULL_OBJECT = new GeneralResponseTemp("ERR001",
            "NULL_OBJECT", ResponseCode.ERROR);
    public static final GeneralResponseTemp TOO_LONG = new GeneralResponseTemp("ERR002",
            "TOO_LONG", ResponseCode.ERROR);
    public static final GeneralResponseTemp LESS_THAN_MIN = new GeneralResponseTemp("ERR003",
            "LESS_THAN_MIN", ResponseCode.ERROR);
    public static final GeneralResponseTemp MORE_THAN_MAX = new GeneralResponseTemp("ERR004",
            "MORE_THAN_MAX", ResponseCode.ERROR);
    public static final GeneralResponseTemp NOT_IN_LIST = new GeneralResponseTemp("ERR005",
            "NOT_IN_LIST", ResponseCode.ERROR);
    public static final GeneralResponseTemp PATTERN_ERROR = new GeneralResponseTemp("ERR006",
            "PATTERN_ERROR", ResponseCode.ERROR);
    public static final GeneralResponseTemp MISSING_KEY = new GeneralResponseTemp("ERR007",
            "MISSING_KEY", ResponseCode.ERROR);
    public static final GeneralResponseTemp TOO_SHORT = new GeneralResponseTemp("ERR008",
            "TOO_SHORT", ResponseCode.ERROR);
    public static final GeneralResponseTemp FORMAT_ERROR = new GeneralResponseTemp("ERR998",
            "FORMAT_ERROR", ResponseCode.ERROR);
    public static final GeneralResponseTemp ERR999 = new GeneralResponseTemp("ERR999",
            "OTHER_ERROR", ResponseCode.ERROR);
}
