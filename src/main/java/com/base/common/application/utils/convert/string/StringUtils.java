package com.base.common.application.utils.convert.string;

public class StringUtils extends org.springframework.util.StringUtils {
    public static String maskingData(String str, Integer start, Integer stop) {
        Integer charCount = str.length();
        if (charCount < start)
            start = charCount;
        if (stop < 0)
            stop = charCount + stop;
        if (charCount < stop || stop < 0)
            stop = charCount;
        String out = str.substring(0, start) + "***" + str.substring(stop, str.length());
        return out;
    }
}
