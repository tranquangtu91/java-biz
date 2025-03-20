package com.base.common.application.utils.sql;

import com.base.common.application.utils.sql.impl.mysql.MySQLCriteriaBuilderHandler;

public class CriteriaBuilderHandlerFactory {
    public static ICriteriaBuilderHandler getCriteriaBuilderHandler(String type) {
        return new MySQLCriteriaBuilderHandler();
    }
}
