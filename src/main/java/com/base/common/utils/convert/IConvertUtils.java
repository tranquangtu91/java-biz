package com.base.common.utils.convert;

public interface IConvertUtils<T> {
    T parse(Object data, ConvertOption[] convertOptions);
}
