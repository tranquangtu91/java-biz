package com.base.common.utils.convert.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;
import org.springframework.util.SerializationUtils;

import com.base.common.utils.convert.string.CasingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtils extends org.springframework.util.ObjectUtils {
    public final static ObjectMapper objectMapper = new ObjectMapper();
    static Map<String, List<Field>> declaredFieldsCache = new HashMap<>();
    public static Boolean verbose = false;

    public final static Integer MODIFIER_PRIVATE = 0;
    public final static Integer MODIFIER_PUBLIC = 1;

    /**
     * Bỏ bớt trường dữ liệu
     * 
     * @param item
     * @param excludeKeys
     * @returns
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> modifyData(Object item,
            String[] excludeKeys,
            ModifyDataOptions params) {
        if (isEmpty(item))
            return objectToMap(item);

        Map<String, Object> __item = objectToMap(item);
        if (!isEmpty(excludeKeys)) {
            for (String key : excludeKeys) {
                __item.remove(key);
            }
        }

        String[] keySet = (__item.keySet().toArray(new String[0]));
        for (String key : keySet) {
            if (params != null) {
                Object __value = __item.get(key);
                if (__value == null && params.removeNullObject) {
                    if (ObjectUtils.isEmpty(__item.get(key))) {
                        __item.remove(key);
                    }
                } else if (__value instanceof Map && params.depthModify) {
                    __value = modifyData(__value, excludeKeys, params);
                    __item.put(key, __value);
                } else if (__value instanceof List && params.depthModify) {
                    __value = ((ArrayList<Object>) __value).stream().map(it -> {
                        try {
                            return modifyData(it, excludeKeys, params);
                        } catch (Exception ex) {
                            return it;
                        }
                    }).collect(Collectors.toList());
                    __item.put(key, __value);
                }
            }
        }

        return __item;
    }

    public static <T> Map<String, Object> modifyData(T item, @Nullable String[] excludeKeys) {
        ModifyDataOptions modifyDataOptions = new ModifyDataOptions() {
            {
                removeNullObject = true;
                depthModify = true;
            }
        };
        return modifyData(item, excludeKeys, modifyDataOptions);
    }

    /**
     * Bỏ bớt trường dữ liệu nhiều object một lúc
     * 
     * @param <T>
     * @param items
     * @param excludeKeys
     * @return
     */
    public static <T> Object[] modifyData(List<T> items, @Nullable String[] excludeKeys) {
        Object[] mItems = items.stream().map(it -> {
            Map<String, Object> item = ObjectUtils.modifyData(it, excludeKeys);
            return item;
        }).toArray();
        return mItems;
    }

    public static <T> Object[] modifyData(T[] items, @Nullable String[] excludeKeys) {
        List<Object> __items = new ArrayList<>();
        for (T it : items) {
            Map<String, Object> item = ObjectUtils.modifyData(it, excludeKeys);
            __items.add(item);
        }
        return __items.toArray();
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public static <T> T deepClone(T item) {
        byte[] data = SerializationUtils.serialize(item);
        T obj = (T) SerializationUtils.deserialize(data);
        return obj;
    }

    /**
     * Convert Object to Map
     * 
     * @param item
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objectToMap(Object item) {
        if (item instanceof Map)
            return (Map<String, Object>) item;
        Map<String, Object> __item = null;
        try {
            __item = objectMapper.convertValue(item, Map.class);
        } catch (Exception ex) {
            throw ex;
        }
        return __item;
    }

    public static Object mapToObject(Class<?> clazz, Map<String, Object> value) {
        Object obj = objectMapper.convertValue(value, clazz);
        return obj;
    }

    /**
     * Merge gia tri tu src vao dst
     * 
     * @param dst
     * @param src
     * @param keepFields
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    @SuppressWarnings("unchecked")
    public static void mergeObject(Object dst, Object src, List<String> keepFields) {
        if (keepFields == null)
            keepFields = new ArrayList<>();
        Class<?> srcClazz = src.getClass();
        List<String> srcFields;
        if (src instanceof Map) {
            Set<String> __keySet = ((Map<String, Object>) src).keySet();
            srcFields = Arrays.asList(__keySet.toArray(new String[0]));
        } else {
            srcFields = ReflectUtils.getDeclaredFieldNames(srcClazz);
        }
        for (String field : srcFields) {
            if (!keepFields.contains(field)) {
                Object srcValue = getFieldValue(src, field);
                setFieldValue(dst, field, srcValue);
            }
        }
    }

    public static void mergeObject(Object dst, Object src) {
        mergeObject(dst, src, null);
    }

    @SuppressWarnings("unchecked")
    public static Object getFieldValue(Object item, String field) {
        List<String> __fields = new ArrayList<>();
        __fields.addAll(Arrays.asList(field.split("\\.")));
        Object value = null;

        if (__fields.size() > 1) {
            value = getFieldValue(item, __fields.get(0));
            __fields.remove(0);
            value = getFieldValue(value, String.join(".", __fields));
            return value;
        }

        if (item instanceof Map) {
            value = ((Map<String, Object>) item).get(field);
            return value;
        }

        Field __field = ReflectUtils.getField(item.getClass(), field);

        if (__field == null)
            return null;

        if (__field.getModifiers() == MODIFIER_PRIVATE) {
            Method method = ReflectUtils.getMemberValueMethod(item.getClass(), field);

            if (method == null)
                return null;
            try {
                return method.invoke(item);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                log.error(String.format("%s: %s", e.getClass().getName(), e.getMessage()));
                if (verbose) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                value = __field.get(item);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error(String.format("%s: %s", e.getClass().getName(), e.getMessage()));
                if (verbose) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public static Boolean containsKey(Object item, String field) {
        Boolean rs = false;

        List<String> __fields = new ArrayList<>();
        __fields.addAll(Arrays.asList(field.split("\\.")));

        if (__fields.size() > 1) {
            Object __item = getFieldValue(item, __fields.get(0));
            __fields.remove(0);
            rs = containsKey(__item, String.join(".", __fields));
            return rs;
        }

        List<String> fieldNames;
        if (item instanceof Map) {
            fieldNames = new ArrayList<>();
            fieldNames.addAll(((Map<String, Object>) item).keySet());
        } else {
            fieldNames = ReflectUtils.getDeclaredFieldNames(item.getClass());
        }
        rs = fieldNames.contains(field);

        return rs;
    }

    @SuppressWarnings("unchecked")
    public static void setFieldValue(Object item, String field, Object value) {
        List<String> __fields = new ArrayList<>();
        __fields.addAll(Arrays.asList(field.split("\\.")));

        if (__fields.size() > 1) {
            Object __item = getFieldValue(item, __fields.get(0));
            __fields.remove(0);
            setFieldValue(__item, String.join(".", __fields), value);
            return;
        }

        if (item instanceof Map) {
            ((Map<String, Object>) item).put(field, value);
            return;
        }

        Field __field = ReflectUtils.getField(item.getClass(), field);

        if (__field == null)
            return;

        Object __value = objectMapper.convertValue(value, __field.getType());

        if (__field.getModifiers() == MODIFIER_PRIVATE) {
            String method = "set" + CasingUtils.upperFirstChar(field);
            try {
                item.getClass().getMethod(method, __field.getType()).invoke(item, __value);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e1) {
                log.error(String.format("%s: %s", e1.getClass().getName(), e1.getMessage()));
                if (verbose) {
                    e1.printStackTrace();
                }
            }
        } else {
            try {
                __field.set(item, __value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error(String.format("%s: %s", e.getClass().getName(), e.getMessage()));
                if (verbose) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> void addArrayToList(List<T> list, T[] arr) {
        for (T it : arr) {
            list.add(it);
        }
    }

    public static <T> T findItemInList(List<T> items, String fieldName, Object value) {
        Optional<T> optional = items.stream().filter(it -> getFieldValue(it, fieldName) == value).findFirst();
        T item = optional.isPresent() ? optional.get() : null;
        return item;
    }
}
