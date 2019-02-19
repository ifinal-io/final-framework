package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:52:04
 * @since 1.0
 */
public class LessThanCriterionOperation<T> extends AbsCriterionOperation<T> {
    public static final LessThanCriterionOperation INSTANCE = new LessThanCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.LESS_THAN.name();
    }

    @Override
    public String format(QProperty property, T value) {
        final String column = getPropertyColumn(property);
        if (value instanceof String) {
            return String.format("%s < '%s'", column, value);
        } else if (value instanceof Date) {
            return String.format("%s < '%s'", column, format((Date) value));
        } else {
            return String.format("%s < %s", column, value.toString());
        }

    }
}
