package com.ilikly.finalframework.core.formatter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 21:47:27
 * @since 1.0
 */
public interface Formatters<T> {
    T parse(String source);
}
