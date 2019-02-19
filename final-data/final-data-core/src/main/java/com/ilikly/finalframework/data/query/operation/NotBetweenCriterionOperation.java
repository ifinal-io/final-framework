package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.BetweenCriterionOperation;
import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:38:23
 * @since 1.0
 */
public class NotBetweenCriterionOperation<T extends Comparable<T>> extends AbsCriterionOperation<T> implements BetweenCriterionOperation<T> {
    public static final ContainsCriterionOperation INSTANCE = new ContainsCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.NOT_BETWEEN.name();
    }

    @Override
    public String format(QProperty property, String operation, T min, T max) {
        final String column = getPropertyColumn(property);
        if (min instanceof String) {
            return String.format("%s NOT BETWEEN '%s' AND '%s'", column, min, max);
        } else if (min instanceof Date) {
            return String.format("%s NOT BETWEEN '%s' AND '%s'", column, format((Date) min), format((Date) max));
        } else {
            return String.format("%s NOT BETWEEN %s AND %s", column, min, max);
        }
    }
}
