package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:38:04
 * @since 1.0
 */
public class EndWithCriterionOperation<T> extends AbsCriterionOperation<T> {
    public static final EndWithCriterionOperation INSTANCE = new EndWithCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.END_WITH.name();
    }

    @Override
    public String format(QProperty property, T value) {
        final String column = getPropertyColumn(property);
        return String.format("%s LIKE '%%%s'", column, value.toString());
    }

}
