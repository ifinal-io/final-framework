package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:38:23
 * @since 1.0
 */
public class BetweenCriterionOperation<T extends Comparable> extends AbsCriterionOperation<T> {
    public static final BetweenCriterionOperation INSTANCE = new BetweenCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.BETWEEN.name();
    }

    @Override
    public String format(QProperty property, T min, T max) {
        final String column = getPropertyColumn(property);
        return min instanceof String ? String.format("%s BETWEEN '%s' AND '%s'", column, min, max) : String.format("%s BETWEEN %s AND %s", column, min, max);
    }
}
