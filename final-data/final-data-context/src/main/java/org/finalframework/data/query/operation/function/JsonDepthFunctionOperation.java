package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 13:10:29
 * @since 1.0
 */
public class JsonDepthFunctionOperation implements FunctionOperation {
    @Override
    public Operation operation() {
        return JsonOperation.JSON_DEPTH;
    }

    @Override
    public String apply(Object value) {
        return String.format("JSON_DEPTH(%s)", value);
    }
}

