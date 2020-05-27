package org.finalframework.data.query.operation;


import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum StringOperation implements Operation {
    CONCAT, LOWER, UPPER;

    public static FunctionOperation concat(String prefix, String suffix) {
        return new DoubleFunctionOperation<>(CONCAT, prefix, suffix);
    }

    public static FunctionOperation lower() {
        return new SimpleFunctionOperation(LOWER);
    }

    public static FunctionOperation upper() {
        return new SimpleFunctionOperation(UPPER);
    }

}

