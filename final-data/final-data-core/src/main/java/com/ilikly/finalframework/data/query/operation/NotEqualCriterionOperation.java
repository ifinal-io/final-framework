package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.SingleCriterionOperation;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:49:33
 * @since 1.0
 */
public class NotEqualCriterionOperation<T> extends AbsCriterionOperation<T> implements SingleCriterionOperation<T> {
    public static final NotEqualCriterionOperation INSTANCE = new NotEqualCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.NOT_EQUAL.name();
    }

    @Override
    public String format(QProperty property, String operation, T value) {
        final String column = getPropertyColumn(property);

        if (value instanceof String) {
            return String.format("%s != '%s'", column, value);
        } else if (value instanceof Date) {
            return String.format("%s != '%s'", column, format((Date) value));
        } else {
            return String.format("%s != %s", column, value.toString());
        }

    }
}
