package com.base.common.application.utils.antixss;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AntiXSSUtils {
    public static Map<String, XSSPatternConfig> patterns = new HashMap<>();

    public static void registryPattern(String key, String regex, Integer flags) {
        XSSPatternConfig patternConfig = new XSSPatternConfig();
        patternConfig.regex = regex;
        flags = flags == null ? 0 : flags;
        patternConfig.pattern = Pattern.compile(regex, flags);

        patterns.put(key, patternConfig);
    }

    public static Boolean hasXSSAttach(String value) {
        if (ObjectUtils.isEmpty(value) || ObjectUtils.isEmpty(patterns))
            return false;

        String[] keySet = patterns.keySet().toArray(new String[0]);
        for (String key : keySet) {
            if (patterns.get(key).pattern.matcher(value).find())
                return true;
        }

        return false;
    }

    public static String stripXSS(String value) {
        if (ObjectUtils.isEmpty(value))
            return null;

        String[] keySet = patterns.keySet().toArray(new String[0]);
        for (String key : keySet) {
            value = patterns.get(key).pattern.matcher(value).replaceAll("");
        }

        return value;
    }

    public static Object antiXSS(Object item, List<String> fields) {
        Field[] declaredFields = item.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType() == String.class && fields.contains(field.getName())) {
                try {
                    Object value = field.get(item);
                    if (value != null && hasXSSAttach(value.toString())) {
                        log.warn("has Cross Site Scripting attack to ${item}.${it} with value '${item[it]}'");
                        field.set(item, stripXSS(value.toString()));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return item;
    }

    public static Object antiXSSAllStringFields(Object item) {
        Field[] declaredFields = item.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Object value;
            try {
                value = field.get(item);
                if (field.getType() == String.class && hasXSSAttach(value.toString())) {
                    log.warn("has Cross Site Scripting attack to ${item}.${it.name} with value '${item[it.name]}'");
                    field.set(item, stripXSS(value.toString()));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
