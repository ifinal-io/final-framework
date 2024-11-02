/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.query;

import java.io.Serializable;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface QEntity<I extends Serializable, T> extends Iterable<QProperty<?>> {

    /**3
     * return entity type
     *
     * @return entity type
     */
    Class<T> getType();

    /**
     * return entity name
     *
     * @return entity name
     */
    default String getName() {
        return getType().getName();
    }

    /**
     * return entity simple name
     *
     * @return simple name
     */
    default String getSimpleName() {
        return getType().getSimpleName();
    }

    /**
     * return entity mapping table name
     *
     * @return mapping table name
     */
    String getTable();

    /**
     * return id property
     *
     * @return id property
     */
    QProperty<I> getIdProperty();

    /**
     * return version property
     *
     * @param <E> version type
     * @return version property
     */
    <E> QProperty<E> getVersionProperty();

    /**
     * return {@code true} if had a version property
     *
     * @return {@code true} if had a version property
     * @see #getVersionProperty()
     */
    default boolean hasVersionProperty() {
        return getVersionProperty() != null;
    }

    <E> QProperty<E> getTenantProperty();

    default boolean hasTenantProperty() {
        return getTenantProperty() != null;
    }

    /**
     * return property by path
     *
     * @param path path
     * @param <E>  property type
     * @return property
     */
    <E> QProperty<E> getProperty(String path);

    /**
     * return required property of path
     *
     * @param path property path
     * @param <E>  property type
     * @return property
     */
    @NonNull
    default <E> QProperty<E> getRequiredProperty(String path) {
        QProperty<E> property = getProperty(path);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of %s not found for %s!", path, getType()));
    }

    Stream<QProperty<?>> stream();

}
