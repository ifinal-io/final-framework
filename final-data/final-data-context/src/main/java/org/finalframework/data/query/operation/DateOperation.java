package org.finalframework.data.query.operation;


import org.finalframework.data.query.operation.function.Function;
import org.finalframework.data.query.operation.function.DateFunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum DateOperation implements Operation {
    DATE;

    public static Function date() {
        return new DateFunctionOperation();
    }
}

