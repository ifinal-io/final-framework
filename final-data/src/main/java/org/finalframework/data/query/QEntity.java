package org.finalframework.data.query;

import org.finalframework.core.Streamable;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 19:53:09
 * @since 1.0
 */
public interface QEntity<ID extends Serializable, T> extends Streamable<QProperty<?>>, Iterable<QProperty<?>> {

    static QEntity<?, ?> from(Class<?> entity) {
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


    QProperty<ID> getIdProperty();

    QProperty getVersionProperty();

    default boolean hasVersionProperty() {
        return getVersionProperty() != null;
    }

    <E> QProperty<E> getProperty(String path);

    default QProperty getRequiredProperty(String path) {
        QProperty<?> property = getProperty(path);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of %s not found for %s!", path, getType()));
    }

}
