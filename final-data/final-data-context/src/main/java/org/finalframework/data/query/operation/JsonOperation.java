package org.finalframework.data.query.operation;


import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum JsonOperation implements Operation {
    JSON_EXTRACT,
    JSON_CONTAINS,
    JSON_UNQUOTE,
    JSON_KEYS,
    JSON_DEPTH,
    JSON_LENGTH;

    public static FunctionOperation extract(@NonNull String path) {
        return new SingleFunctionOperation<>(JSON_EXTRACT, path);
    }

    public static FunctionOperation unquote() {
        return new SimpleFunctionOperation(JSON_UNQUOTE);
    }


    public static FunctionOperation keys() {
        return new SimpleFunctionOperation(JSON_KEYS);
    }


    public static FunctionOperation depth() {
        return new SimpleFunctionOperation(JSON_DEPTH);
    }


    public static FunctionOperation length() {
        return new SimpleFunctionOperation(JSON_LENGTH);
    }


}

