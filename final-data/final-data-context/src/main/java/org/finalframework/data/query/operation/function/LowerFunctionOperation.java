package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.Operation;
import org.finalframework.data.query.operation.StringOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 12:53:09
 * @since 1.0
 */
public class LowerFunctionOperation implements FunctionOperation {
    @Override
    public Operation operation() {
        return StringOperation.LOWER;
    }

    @Override
    public String apply(Object value) {
        return String.format("LOWER(%s)", value);
    }
}

