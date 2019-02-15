package com.ilikly.finalframework.core.datetime;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 21:41:47
 * @since 1.0
 */
public interface DateTimeFormatter<T> {
    T parse(String source);

    String format(T date);
}
