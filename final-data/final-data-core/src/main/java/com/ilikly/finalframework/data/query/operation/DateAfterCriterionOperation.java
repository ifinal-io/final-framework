package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.CriterionOperators;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.SingleCriterionOperation;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:52:04
 * @since 1.0
 */
public class DateAfterCriterionOperation extends AbsCriterionOperation<Date> implements SingleCriterionOperation<Date> {
    public static final DateAfterCriterionOperation INSTANCE = new DateAfterCriterionOperation();

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.DATE_AFTER;
    }

    @Override
    public String format(QProperty property, CriterionOperator operator, Date value) {
        final String column = getPropertyColumn(property);
        return String.format("DATE(%s) > '%s'", column, format(value));
    }
}
