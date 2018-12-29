package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.core.Streable;
import com.ilikly.finalframework.data.entity.IEntity;
import com.ilikly.finalframework.data.mapping.Entity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 19:53:09
 * @since 1.0
 */
public interface QEntity<ID extends Serializable, T extends IEntity<ID>> extends Streable<QProperty>, Iterable<QProperty> {

    static <ID extends Serializable, T extends IEntity<ID>> QEntity<ID, T> from(Class<T> entity) {
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

        throw new IllegalStateException(String.format("Required property not found for %s!", getType()));

    }

}
