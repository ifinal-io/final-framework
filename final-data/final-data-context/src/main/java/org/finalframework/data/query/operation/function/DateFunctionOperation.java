package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-29 20:27:09
 * @see DateOperation#DATE
 * @since 1.0
 */
public final class DateFunctionOperation implements FunctionOperation {
    @Override
    public String apply(Object value) {
        return String.format("DATE(%s)", value);
    }

    @Override
    public final Operation operation() {
        return DateOperation.DATE;
    }
}

