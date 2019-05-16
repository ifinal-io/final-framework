package org.finalframework.data.query;

import org.finalframework.core.Streamable;
import org.finalframework.data.mapping.Entity;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 19:53:09
 * @since 1.0
 */
public interface QEntity<ID extends Serializable, T> extends Streamable<QProperty>, Iterable<QProperty> {

    static <ID extends Serializable, T> QEntity<ID, T> from(Class<T> entity) {
        return new BaseQEntity<>(Entity.from(entity));
    }

    Class<ID> getIdType();

    Class<T> getType();

    <E> QProperty<E> getIdProperty();

    default <E> QProperty<E> getRequiredIdProperty() {
        QProperty property = getIdProperty();

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required idProperty not found for %s!", getType()));

    }

    <E> QProperty<E> getProperty(String path);

    default <E> QProperty<E> getRequiredProperty(String path) {
        QProperty property = getProperty(path);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of path = %s not found for %s!", path, getType()));

    }

    <E> QProperty<E> getPropertyByName(String name);

    default <E> QProperty<E> getRequiredPropertyByName(String name) {
        QProperty property = getPropertyByName(name);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of name = %s not found for %s!", name, getType()));

    }

    <E> QProperty<E> getPropertyByColumn(String column);

    default <E> QProperty<E> getRequiredPropertyByColumn(String column) {
        QProperty property = getPropertyByColumn(column);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of column = %s not found for %s!", column, getType()));

    }


    @Nullable
    Collection<Class<?>> getViews();

}
