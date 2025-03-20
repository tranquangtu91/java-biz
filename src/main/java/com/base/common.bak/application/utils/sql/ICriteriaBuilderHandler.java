package com.base.common.application.utils.sql;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public interface ICriteriaBuilderHandler {
    public Expression<?> functionJsonExtract(CriteriaBuilder cb, Root<?> root, String columnName, String key,
            Class<?> clazz);

    public Expression<?> functionCount(CriteriaBuilder cb, Root<?> root, String columnName);

    public Expression<?> getKey(Path<?> path, String key);
}
