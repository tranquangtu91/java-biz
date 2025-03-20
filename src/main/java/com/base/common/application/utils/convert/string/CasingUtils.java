package com.base.common.application.utils.convert.string;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.base.common.application.utils.convert.object.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CasingUtils {

    public static String upperFirstChar(String str) {
        if (ObjectUtils.isEmpty(str))
            return str;
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    public static String[] splitText(String str) {
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), "-" + lowerFirstChar(m.group()));
        }
        return str.split("[-_]");
    }

    public static String lowerFirstChar(String str) {
        if (ObjectUtils.isEmpty(str))
            return str;
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        str = str.substring(0, 1).toLowerCase() + str.substring(1);
        return str;
    }

    public static String toSnake(String[] params) {
        List<String> convertedParams = Arrays.asList(params).stream().map(str -> lowerFirstChar(str)).filter(str -> !ObjectUtils.isEmpty(str))
                .collect(Collectors.toList());
        String value = String.join("_", convertedParams);
        return value;
    }

    public static String toSnake(String str) {
        return toSnake(splitText(str));
    }

    public static String toKebab(String[] params) {
        String value = toSnake(params);
        value = value.replaceAll("_", "-");
        return value;
    }

    public static String toKebab(String str) {
        return toKebab(splitText(str));
    }

    public static String toPascal(String[] params) {
        List<String> convertedParams = Arrays.asList(params).stream().map(str -> upperFirstChar(str)).filter(str -> !ObjectUtils.isEmpty(str))
                .collect(Collectors.toList());
        String value = String.join("", convertedParams);
        return value;
    }
    
    public static String toPascal(String str) {
        return toPascal(splitText(str));
    }

    public static String toCamel(String[] params) {
        String value = toPascal(params);
        value = lowerFirstChar(value);
        return value;
    }
    
    public static String toCamel(String str) {
        return toCamel(splitText(str));
    }
}
