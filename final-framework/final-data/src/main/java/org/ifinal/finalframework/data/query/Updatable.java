package org.ifinal.finalframework.data.query;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Updatable<T> {

    T set(Object value);

    T inc();

    T incy(Number value);

    T dec();

    T decy(Number value);

}
