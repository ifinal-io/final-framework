package org.finalframework.core.formatter;

import org.finalframework.core.filter.Filter;

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
