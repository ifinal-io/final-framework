package com.ilikly.finalframework.data.mapping.function;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-23 13:20:10
 * @since 1.0
 */
public interface FunctionHandler<T> {

    String to(T value);

    T from(String text);

}
