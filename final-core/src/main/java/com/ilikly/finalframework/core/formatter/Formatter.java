package com.ilikly.finalframework.core.formatter;

import com.ilikly.finalframework.core.filter.Filter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 21:43:32
 * @since 1.0
 */
public interface Formatter<T> extends Filter<String> {

    T parse(String source);

    String format(T target);
}
