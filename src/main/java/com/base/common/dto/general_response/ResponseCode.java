package com.base.common.dto.general_response;

public enum ResponseCode {
    SUCCESS(0),
    ERROR(-1),
    ERROR_CAN_RETRY(-2);

    public final Integer value;

    private ResponseCode(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
