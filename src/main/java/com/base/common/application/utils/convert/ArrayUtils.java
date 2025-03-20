package com.base.common.application.utils.convert;

import java.util.List;

import org.springframework.util.ObjectUtils;

public class ArrayUtils {
    public static <T> T getLatestObject(T[] data) {
        if (ObjectUtils.isEmpty(data))
            return null;
        Integer latestIdx = data.length - 1;
        return data[latestIdx];
    }

    public static <T> T getLatestObject(List<T> data) {
        if (ObjectUtils.isEmpty(data))
            return null;
        Integer latestIdx = data.size() - 1;
        return data.get(latestIdx);
    }
}
