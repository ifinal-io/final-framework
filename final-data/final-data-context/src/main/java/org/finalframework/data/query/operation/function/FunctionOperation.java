package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-29 20:25:55
 * @since 1.0
 */
public interface FunctionOperation extends Function, Operation {

    @Override
    default String name() {
        return operation().name();
    }

    @NonNull
    Operation operation();
}

