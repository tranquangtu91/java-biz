package com.base.common.utils.sql;

public class CriteriaBuilderHandlerFactory {
    public static ICriteriaBuilderHandler getCriteriaBuilderHandler(String type) {
        return new MySQLCriteriaBuilderHandler();
    }
}
