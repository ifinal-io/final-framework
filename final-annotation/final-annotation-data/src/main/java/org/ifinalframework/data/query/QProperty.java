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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.core.Sortable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface QProperty<T> extends Comparable<QProperty<T>>, FunctionCriteriable<Object>, Sortable<Order> {

    /**
     * return entity
     *
     * @param <E> entity type
     * @return entity
     */
    <E extends QEntity<?, ?>> E getEntity();

    /**
     * return property type
     *
     * @return property type
     */
    Class<T> getType();

    Type getGenericType();

    /**
     * return order
     *
     * @return order
     */
    Integer getOrder();

    /**
     * return path
     *
     * @return path
     */
    String getPath();

    /**
     * return table
     *
     * @return table
     */
    String getTable();

    /**
     * return name
     *
     * @return name
     */
    String getName();

    /**
     * return writer
     *
     * @return writer
     */
    @Nullable
    String getInsert();

    @Nullable
    String getUpdate();

    /**
     * return reader
     *
     * @return reader
     */
    @Nullable
    String getReader();

    /**
     * return column
     *
     * @return column
     */
    String getColumn();

    /**
     * return {@code true} if was an id property
     *
     * @return {@code true} if ware an id property
     */
    boolean isIdProperty();

    /**
     * return whether is version property
     * @return whether is version property
     */
    boolean isVersionProperty();

    /**
     * return can read
     * @return can read
     */
    boolean isReadable();

    /**
     * return can write
     * @return can write
     */
    boolean isWriteable();

    /**
     * return can modifiable
     * @return can modifiable
     */
    boolean isModifiable();

    /**
     * type handler
     * @return type handler
     */
    Class<?> getTypeHandler();

    /**
     * return whether is annotation present
     *
     * @param annotation annotation
     * @return whether is annotation present
     */
    boolean isAnnotationPresent(Class<? extends Annotation> annotation);

    /**
     * return whether is array.
     *
     * @return whether is array.
     */
    boolean isArray();

    /**
     * return has the view
     *
     * @param view view
     * @return has the view
     */
    boolean hasView(@Nullable Class<?> view);

    /**
     * return unique
     *
     * @return unique
     */
    boolean unique();

    /**
     * return nonnull
     *
     * @return nonnull
     */
    boolean nonnull();

    @NonNull
    @Override
    default Criterion condition(@NonNull String expression, @Nullable Object value, @Nullable Consumer<CriterionAttributes> consumer) {
        return CriterionTarget.from(getColumn()).condition(expression, value, consumer);
    }

    @Override
    default Criteriable<Object> apply(@NonNull UnaryOperator<String> column, @Nullable Consumer<CriterionAttributes> consumer) {
        return CriterionTarget.from(getColumn()).apply(column, consumer);
    }

    @Override
    default Order asc() {
        return Order.asc(this.getColumn());
    }

    @Override
    default Order desc() {
        return Order.desc(this.getColumn());
    }

    @Override
    default int compareTo(QProperty<T> o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

}
