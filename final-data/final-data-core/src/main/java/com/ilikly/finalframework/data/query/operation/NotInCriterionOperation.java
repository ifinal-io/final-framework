package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CollectionCriterionOperation;
import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;

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
    public String name() {
        return CriterionOperations.NOT_IN.name();
    }

    @Override
    public String format(QProperty property, String operation, Collection<E> value) {
        final String column = getPropertyColumn(property);
        return String.format("%s Not IN %s", column, buildInString(value));
    }
}
