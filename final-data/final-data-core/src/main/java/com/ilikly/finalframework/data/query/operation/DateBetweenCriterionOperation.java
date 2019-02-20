package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.BetweenCriterionOperation;
import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.CriterionOperators;
import com.ilikly.finalframework.data.query.QProperty;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:38:23
 * @since 1.0
 */
public class DateBetweenCriterionOperation extends AbsCriterionOperation<Date> implements BetweenCriterionOperation<Date> {
    public static final DateBetweenCriterionOperation INSTANCE = new DateBetweenCriterionOperation();

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.DATE_BETWEEN;
    }

    @Override
    public String format(QProperty property, CriterionOperator operator, Date min, Date max) {
        final String column = getPropertyColumn(property);
        return String.format("DATE(%s) BETWEEN '%s' AND '%s'", column, format(min), format(max));
    }
}
