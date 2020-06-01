package org.finalframework.data.query.operation;


import org.finalframework.data.query.operation.function.Function;
import org.finalframework.data.query.operation.function.ConcatFunctionOperation;
import org.finalframework.data.query.operation.function.LowerFunctionOperation;
import org.finalframework.data.query.operation.function.UpperFunctionOperation;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum StringOperation implements Operation {
    CONCAT, LOWER, UPPER;

    public static Function concat(String prefix, String suffix) {
        return new ConcatFunctionOperation(prefix, suffix);
    }

    public static Function lower() {
        return new LowerFunctionOperation();
    }

    public static Function upper() {
        return new UpperFunctionOperation();
    }

}

