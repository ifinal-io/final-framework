package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.criterion.CollectionCriterionOperation;
import com.ilikly.finalframework.data.query.criterion.CriterionOperator;
import com.ilikly.finalframework.data.query.criterion.CriterionOperators;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:25:39
 * @since 1.0
 */
public class NotInCriterionOperation<E> extends AbsCollectionCriterionOperation<E> implements CollectionCriterionOperation<E> {
    public static final NotInCriterionOperation INSTANCE = new NotInCriterionOperation();

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.NOT_IN;
    }

    @Override
    public String format(QProperty property, CriterionOperator operator, Collection<E> value) {
        final String column = getPropertyColumn(property);
        return String.format("%s Not IN %s", column, buildInString(value));
    }
}
