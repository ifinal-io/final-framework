package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:52:04
 * @since 1.0
 */
public class GreaterEqualThanCriterionOperation<T> extends AbsCriterionOperation<T> {
    public static final GreaterEqualThanCriterionOperation INSTANCE = new GreaterEqualThanCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.GREATER_EQUAL_THAN.name();
    }

    @Override
    public String format(QProperty property, T value) {
        final String column = getPropertyColumn(property);
        return value instanceof String ? String.format("%s >= '%s'", column, value) : String.format("%s >= %s", column, value.toString());
    }
}
