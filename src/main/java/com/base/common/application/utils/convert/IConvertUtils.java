package com.base.common.application.utils.convert;

public interface IConvertUtils<T> {
    T parse(Object data, ConvertOption[] convertOptions);
}
