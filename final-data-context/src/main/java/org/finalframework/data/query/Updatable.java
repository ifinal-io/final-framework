package org.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-26 13:08
 * @since 1.0
 */
public interface Updatable<T> {

    T set(Object value);

    T inc();

    T incy(Number value);

    T dec();

    T decy(Number value);

}
