package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 13:12:27
 * @since 1.0
 */
public class JsonExtractFunctionOperation implements FunctionOperation {
    private final String path;

    public JsonExtractFunctionOperation(String path) {
        this.path = path;
    }

    @Override
    public Operation operation() {
        return JsonOperation.JSON_EXTRACT;
    }

    @Override
    public String apply(Object value) {
        return String.format("JSON_EXTRACT(%s,'%s')", value, path);
    }
}

