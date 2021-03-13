package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.util.stream.Streamable;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface QEntity<I extends Serializable, T> extends Streamable<QProperty<?>>, Iterable<QProperty<?>> {

    static <I extends Serializable, T> QEntity<I, T> from(Class<T> entity) {
        return new AbsQEntity<>(entity);
    }

    Class<T> getType();

    default String getName() {
        return getType().getName();
    }

    default String getSimpleName() {
        return getType().getSimpleName();
    }

    String getTable();

    QProperty<I> getIdProperty();

    <E> QProperty<E> getVersionProperty();

    default boolean hasVersionProperty() {
        return getVersionProperty() != null;
    }

    <E> QProperty<E> getProperty(String path);

    default <E> QProperty<E> getRequiredProperty(String path) {
        QProperty<E> property = getProperty(path);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of %s not found for %s!", path, getType()));
    }

}
