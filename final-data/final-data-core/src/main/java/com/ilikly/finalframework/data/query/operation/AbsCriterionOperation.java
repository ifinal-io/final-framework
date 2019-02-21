package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.core.formatter.DateFormatter;
import com.ilikly.finalframework.data.query.QProperty;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterionOperation<T> {

    private static final Set<String> SQL_KEYS = new HashSet<>();
    private static final DateFormatter dateformatter = DateFormatter.YYYY_MM_DD_HH_MM_SS;

    static {
        SQL_KEYS.add("key");
    }

    protected String getPropertyColumn(QProperty property) {
        return SQL_KEYS.contains(property.getColumn().toLowerCase()) ?
                String.format("`%s`",property.getColumn()) : property.getColumn();
    }

    protected String format(Date date) {
        return dateformatter.format(date);
    }

}
