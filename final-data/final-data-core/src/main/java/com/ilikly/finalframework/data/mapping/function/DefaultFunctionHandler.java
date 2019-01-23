package com.ilikly.finalframework.data.mapping.function;

import java.lang.reflect.Constructor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-23 13:24:48
 * @since 1.0
 */
public class DefaultFunctionHandler<T> implements FunctionHandler<T> {
    private final Class<T> type;
    private final Constructor constructor;

    public DefaultFunctionHandler(Class<T> type) {
        try {
            this.type = type;
            this.constructor = type.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("the function type of %s must have the constructor with String.class.", type),e);
        }
    }

    @Override
    public String to(T value) {
        return value.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T from(String text) {
        try {
            return (T) constructor.newInstance(text);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
