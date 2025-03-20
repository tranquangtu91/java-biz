package com.base.common.application.utils.convert.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.base.common.application.utils.convert.string.CasingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectUtils {

    public final static ObjectMapper objectMapper = new ObjectMapper();
    static Map<String, List<Field>> declaredFieldsCache = new HashMap<>();
    static Map<String, List<Method>> declaredMethodsCache = new HashMap<>();
    public static Boolean verbose = false;

    public final static Integer MODIFIER_PRIVATE = 0;
    public final static Integer MODIFIER_PUBLIC = 1;

    /**
     * Trả về tất cả các field của domainObject
     * 
     * @param domainClass
     * @param excludes    danh sách các field mặc định không trả ra
     * @return
     */
    public static List<Field> getDeclaredFields(Class<?> domainClass, List<String> excludes) {
        if (excludes == null)
            excludes = new ArrayList<>();
        List<Field> declaredFields;
        String className = domainClass.getName();
        if (declaredFieldsCache.containsKey(domainClass.getName())) {
            declaredFields = declaredFieldsCache.get(className);
        } else {
            log.debug(String.format("calc declared fields of '%s'...", className));
            declaredFields = new ArrayList<>();
            ObjectUtils.addArrayToList(declaredFields, domainClass.getDeclaredFields());
            ObjectUtils.addArrayToList(declaredFields, domainClass.getSuperclass().getDeclaredFields());
            declaredFieldsCache.put(className, declaredFields);
        }
        return declaredFields;
    }

    public static List<Field> getDeclaredFields(Class<?> domainClass) {
        return getDeclaredFields(domainClass, null);
    }

    /**
     * Trả về danh sách field name của object
     * 
     * @param domainClass
     * @param excludes
     * @return
     */
    public static List<String> getDeclaredFieldNames(Class<?> domainClass, List<String> excludes) {
        List<Field> fields = getDeclaredFields(domainClass, excludes);
        List<String> fieldNames = fields.stream().map(it -> it.getName()).collect(Collectors.toList());
        return fieldNames;
    }

    public static List<String> getDeclaredFieldNames(Class<?> domainClass) {
        return getDeclaredFieldNames(domainClass, null);
    }

    public static List<Method> getDeclaredMethods(Class<?> domainClass, List<String> excludes) {
        if (excludes == null)
            excludes = new ArrayList<>();
        List<Method> declaredMethods;
        String className = domainClass.getName();
        if (declaredMethodsCache.containsKey(domainClass.getName())) {
            declaredMethods = declaredMethodsCache.get(className);
        } else {
            log.debug(String.format("calc declared methods of '%s'...", className));
            declaredMethods = new ArrayList<>();
            ObjectUtils.addArrayToList(declaredMethods, domainClass.getDeclaredMethods());
            ObjectUtils.addArrayToList(declaredMethods, domainClass.getSuperclass().getDeclaredMethods());
            declaredMethodsCache.put(className, declaredMethods);
        }
        return declaredMethods;
    }

    public static List<Method> getDeclaredMethods(Class<?> domainClass) {
        return getDeclaredMethods(domainClass, null);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        List<String> __fieldNames = new ArrayList<>();
        __fieldNames.addAll(Arrays.asList(fieldName.split("\\.")));

        List<Field> fields = ReflectUtils.getDeclaredFields(clazz);
        for (Field field : fields) {
            if (ObjectUtils.nullSafeEquals(field.getName(), __fieldNames.get(0))) {
                if (__fieldNames.size() > 1) {
                    __fieldNames.remove(0);
                    field = getField(field.getType(), String.join(".", __fieldNames));
                    return field;
                }
                return field;
            }
        }
        return null;
    }

    public static Method findMethod(Class<?> clazz, String methodName) {
        List<Method> methods = ReflectUtils.getDeclaredMethods(clazz);
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static Method getMemberValueMethod(Class<?> clazz, String fieldName) {
        String methodName = "get" + CasingUtils.upperFirstChar(fieldName);
        Method method = findMethod(clazz, methodName);
        return method;
    }

    public static Method setMemberValueMethod(Class<?> clazz, String fieldName) {
        String methodName = "set" + CasingUtils.upperFirstChar(fieldName);
        Method method = findMethod(clazz, methodName);
        return method;
    }
}
