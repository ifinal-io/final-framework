package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.criterion.CriterionOperator;
import com.ilikly.finalframework.data.query.criterion.CriterionOperators;
import com.ilikly.finalframework.data.query.criterion.SingleCriterionOperation;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:52:04
 * @since 1.0
 */
public class BeforeCriterionOperation extends AbsCriterionOperation<Date> implements SingleCriterionOperation<Date> {
    public static final BeforeCriterionOperation INSTANCE = new BeforeCriterionOperation();

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.BEFORE;
    }

    @Override
    public String format(QProperty property, CriterionOperator operator, Date value) {
        final String column = getPropertyColumn(property);
        return String.format("%s < '%s'", column, format(value));
    }
}
