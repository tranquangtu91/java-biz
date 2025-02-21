package com.base.common.utils.sql;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class MySQLCriteriaBuilderHandler extends ACriteriaBuilderHandler {
    /**
     * Tùy chỉnh theo loại datasource
     * 
     * @param cb
     * @param root
     * @param key
     * @return
     */
    public Expression<?> functionJsonExtract(CriteriaBuilder cb, Root<?> root, String columnName, String key,
            Class<?> clazz) {
        Expression<?> func1 = cb.function("JSON_EXTRACT", clazz, getKey(root, columnName), cb.literal("$." + key));
        return func1;
    }

    /**
     *
     * @param cb
     * @param root
     * @param columnName
     * @return
     */
    public Expression<?> functionCount(CriteriaBuilder cb, Root<?> root, String columnName) {
        Expression<?> func = cb.function("COUNT", Integer.class, getKey(root, columnName));
        return func;
    }
}
