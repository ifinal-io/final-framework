package org.finalframework.data.query.function;


import org.finalframework.data.query.function.operation.FunctionOperation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-25 10:31:15
 * @since 1.0
 */
public class FunctionValue<T> {
    private final T value;
    private final Collection<FunctionOperation> functions = new ArrayList<>();

    public FunctionValue(T value) {
        this.value = value;
    }

    public FunctionValue(T value, Collection<? extends FunctionOperation> functions) {
        this.value = value;
        this.functions.addAll(functions);
    }

    public T getValue() {
        return value;
    }

    public Collection<? extends FunctionOperation> getFunctions() {
        return functions;
    }

}

