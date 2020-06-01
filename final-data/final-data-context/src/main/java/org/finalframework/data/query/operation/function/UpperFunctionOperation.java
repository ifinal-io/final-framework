package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.Operation;
import org.finalframework.data.query.operation.StringOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 12:51:43
 * @since 1.0
 */
public class UpperFunctionOperation implements FunctionOperation {
    @Override
    public Operation operation() {
        return StringOperation.UPPER;
    }

    @Override
    public String apply(Object value) {
        return String.format("UPPER(%s)", value);
    }
}

