package com.base.common.utils.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;

public abstract class ACriteriaBuilderHandler implements ICriteriaBuilderHandler {

    @Override
    public Expression<?> getKey(Path<?> path, String key) {
        List<String> __keys = new ArrayList<>();
        __keys.addAll(Arrays.asList(key.split("\\.")));

        Path<?> __path = path.get(__keys.get(0));
        if (__keys.size() == 1) {
            return __path;
        }

        __keys.remove(0);
        return getKey(__path, String.join(".", __keys));
    }
}
