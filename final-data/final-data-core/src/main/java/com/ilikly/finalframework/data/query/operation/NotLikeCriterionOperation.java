package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.SingleCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:38:04
 * @since 1.0
 */
public class NotLikeCriterionOperation<T> extends AbsCriterionOperation<T> implements SingleCriterionOperation<T> {
    public static final NotLikeCriterionOperation<String> INSTANCE = new NotLikeCriterionOperation<>();

    @Override
    public String name() {
        return CriterionOperations.NOT_LIKE.name();
    }

    @Override
    public String format(QProperty property, String operation, T value) {
        final String column = getPropertyColumn(property);
        return String.format("%s LIKE '%s'", column, value.toString());
    }

}
