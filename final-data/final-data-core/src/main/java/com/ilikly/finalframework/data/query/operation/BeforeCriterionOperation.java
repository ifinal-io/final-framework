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
public class BeforeCriterionOperation extends AbsCriterionOperation<Date> {
    public static final BeforeCriterionOperation INSTANCE = new BeforeCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.BEFORE.name();
    }

    @Override
    public String format(QProperty property, Date value) {
        final String column = getPropertyColumn(property);
        return String.format("%s < '%s'", column, format(value));
    }
}
