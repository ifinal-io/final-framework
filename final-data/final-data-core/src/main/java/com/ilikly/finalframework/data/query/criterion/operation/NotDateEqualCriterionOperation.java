package com.ilikly.finalframework.data.query.criterion.operation;


import com.ilikly.finalframework.core.formatter.DateFormatter;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.criterion.CriterionOperator;
import com.ilikly.finalframework.data.query.criterion.CriterionOperators;
import com.ilikly.finalframework.data.query.criterion.SingleCriterionOperation;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-26 10:31:28
 * @since 1.0
 */
public class NotDateEqualCriterionOperation extends AbsCriterionOperation<Date> implements SingleCriterionOperation<Date> {

    public static final NotDateEqualCriterionOperation INSTANCE = new NotDateEqualCriterionOperation();

    private static DateFormatter dateFormatter = DateFormatter.YYYY_MM_DD;

    @Override
    public String format(QProperty property, CriterionOperator operator, Date value) {
        final String column = getPropertyColumn(property);
        return String.format("DATE(%s) != '%s'", column, dateFormatter.format(value));
    }

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.DATE_EQUAL;
    }
}
