package com.base.common.utils.sql;

import com.base.common.utils.sql.impl.mysql.MySQLCriteriaBuilderHandler;

public class CriteriaBuilderHandlerFactory {
    public static ICriteriaBuilderHandler getCriteriaBuilderHandler(String type) {
        return new MySQLCriteriaBuilderHandler();
    }
}
