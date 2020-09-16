package org.finalframework.data.query.operation;


import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum DateOperation implements Operation {
    DATE;

    public static CriterionFunction date(Object value) {
        return new SimpleCriterionFunction(value, DATE);
    }
}

