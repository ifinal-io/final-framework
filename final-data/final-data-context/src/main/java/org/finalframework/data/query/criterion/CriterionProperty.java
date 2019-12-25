package org.finalframework.data.query.criterion;


import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.function.operation.FunctionOperation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-25 11:19:24
 * @since 1.0
 */
public class CriterionProperty<T> {
    private final QProperty<T> property;
    private final Collection<FunctionOperation> functions;

    public CriterionProperty(QProperty<T> property, Collection<FunctionOperation> functions) {
        this.property = property;
        this.functions = functions;
    }

    public QProperty<T> getProperty() {
        return property;
    }

    public Collection<FunctionOperation> getFunctions() {
        return functions;
    }
}

