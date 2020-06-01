package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 13:12:27
 * @since 1.0
 */
public class JsonContainsFunctionOperation implements FunctionOperation {
    private final Object value;
    private final String path;

    public JsonContainsFunctionOperation(Object value, String path) {
        this.value = value;
        this.path = path;
    }

    @Override
    public Operation operation() {
        return JsonOperation.JSON_CONTAINS;
    }

    @Override
    public String apply(Object value) {
        if (path == null) {
            return String.format("JSON_CONTAINS(%s,%s)", value, this.value);

        } else {
            return String.format("JSON_CONTAINS(%s,%s,'%s')", value, this.value, path);
        }
    }
}

