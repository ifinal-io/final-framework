package org.finalframework.data.query.operation;

import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;

/**
 * 逻辑运算符
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:24:48
 * @since 1.0
 */
public enum LogicOperation implements Operation {
    AND, OR, NOT, XOR;

    public static <T> FunctionOperation and(T value) {
        return new SingleFunctionOperation<>(AND, value);
    }

    public static <T> FunctionOperation or(T value) {
        return new SingleFunctionOperation<>(OR, value);
    }

    public static FunctionOperation not() {
        return new SimpleFunctionOperation(NOT);
    }

    public static <T> FunctionOperation xor(T value) {
        return new SingleFunctionOperation<>(XOR, value);
    }


}
