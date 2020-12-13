package org.ifinal.finalframework.data.query.operation;

import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.ifinal.finalframework.data.query.criterion.function.SimpleCriterionFunction;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public enum DateOperation implements Operation {
    DATE;

    public static CriterionFunction date(final Object value) {

        return new SimpleCriterionFunction(value, DATE);
    }
}

