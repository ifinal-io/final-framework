package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.criterion.CriterionOperator;
import com.ilikly.finalframework.data.query.criterion.CriterionOperators;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:38:23
 * @since 1.0
 */
public class BetweenCriterionOperation<T extends Comparable<T>> extends AbsCriterionOperation<T> implements com.ilikly.finalframework.data.query.criterion.BetweenCriterionOperation<T> {
    public static final BetweenCriterionOperation INSTANCE = new BetweenCriterionOperation();

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.BETWEEN;
    }

    @Override
    public String format(QProperty property, CriterionOperator operator, T min, T max) {
        final String column = getPropertyColumn(property);
        if (min instanceof String) {
            return String.format("%s BETWEEN '%s' AND '%s'", column, min, max);
        } else if (min instanceof Date) {
            return String.format("%s BETWEEN '%s' AND '%s'", column, format((Date) min), format((Date) max));
        } else {
            return String.format("%s BETWEEN %s AND %s", column, min, max);
        }
    }
}
