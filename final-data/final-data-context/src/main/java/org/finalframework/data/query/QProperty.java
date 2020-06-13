/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Builder;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.criteriable.ExecuteCriteriable;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.CriterionTarget;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.finalframework.data.query.criterion.function.SingleCriterionFunction;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.LogicOperation;
import org.finalframework.data.query.operation.Operation.MathOperation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Criteriable<Object, Criterion>, Sortable<Order>,
        ExecuteCriteriable<T, Criterion> {

    static <T, E extends QEntity<?, ?>> QProperty.Builder<T> builder(E entity, Property property) {
        return new QPropertyImpl.BuilderImpl<>(entity, property);
    }

    <E extends QEntity<?, ?>> E getEntity();

    @NonNull
    Property getProperty();

    @NonNull
    default Class<T> getType() {
        return (Class<T>) getProperty().getJavaType();
    }

    @NonNull
    String getPath();

    @NonNull
    String getTable();

    @NonNull
    String getName();

    @NonNull
    String getColumn();

    boolean isIdProperty();

    /**
     * @return
     * @see Property#isWriteable()
     */
    boolean isWriteable();

    /**
     * @return
     * @see Property#isModifiable()
     */
    boolean isModifiable();

    @NonNull
    PersistentType getPersistentType();

    Class<? extends TypeHandler<?>> getTypeHandler();

    /**
     * Returns whether the property is an array.
     */
    boolean isArray();

    boolean hasView(@Nullable Class<?> view);

    boolean unique();

    boolean nonnull();

    default CriterionTarget<CriterionFunction> apply(Function<QProperty<T>, CriterionFunction> mapper) {
        return CriterionTarget.from(mapper.apply(this));
    }

    @Override
    default Criterion isNull() {
        return CriterionTarget.from(this).isNull();
    }

    @Override
    default Criterion isNotNull() {
        return CriterionTarget.from(this).isNotNull();
    }

    @Override
    default Criterion between(Object min, Object max) {
        return CriterionTarget.from(this).between(min, max);
    }

    @Override
    default Criterion notBetween(Object min, Object max) {
        return CriterionTarget.from(this).notBetween(min, max);
    }

    @Override
    default Criterion eq(Object value) {
        return CriterionTarget.from(this).eq(value);
    }

    @Override
    default Criterion neq(Object value) {
        return CriterionTarget.from(this).neq(value);
    }

    @Override
    default Criterion gt(Object value) {
        return CriterionTarget.from(this).gt(value);
    }

    @Override
    default Criterion gte(Object value) {
        return CriterionTarget.from(this).gte(value);
    }

    @Override
    default Criterion lt(Object value) {
        return CriterionTarget.from(this).lt(value);
    }

    @Override
    default Criterion lte(Object value) {
        return CriterionTarget.from(this).lte(value);
    }

    @Override
    default Criterion in(Collection<Object> values) {
        return CriterionTarget.from(this).in((Collection) values);
    }

    @Override
    default Criterion nin(Collection<Object> values) {
        return CriterionTarget.from(this).nin((Collection) values);
    }

    @Override
    default Criterion like(String value) {
        return CriterionTarget.from(this).like(value);
    }

    @Override
    default Criterion notLike(String value) {
        return CriterionTarget.from(this).notLike(value);
    }

    @Override
    default Criterion jsonContains(@NotNull Object value, String path) {
        return CriterionTarget.from(this).jsonContains(value, path);
//        return new AbsCriteriable<>(this, new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path))
//                .eq(true);
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return CriterionTarget.from(this).notJsonContains(value, path);
//        return new AbsCriteriable<>(this, new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path))
//                .neq(true);
    }


    @Override
    default CriterionTarget<CriterionFunction> date() {
        return apply(value -> new SimpleCriterionFunction(value, DateOperation.DATE));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonExtract(String path) {
        return apply(value -> new SingleCriterionFunction(value, JsonOperation.JSON_EXTRACT, path));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonKeys() {
        return apply(value -> new SimpleCriterionFunction(value, JsonOperation.JSON_KEYS));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonLength() {
        return apply(value -> new SimpleCriterionFunction(value, JsonOperation.JSON_LENGTH));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonDepth() {
        return apply(value -> new SimpleCriterionFunction(value, JsonOperation.JSON_DEPTH));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonUnquote() {
        return apply(value -> new SimpleCriterionFunction(value, JsonOperation.JSON_UNQUOTE));
    }

    @Override
    default CriterionTarget<CriterionFunction> and(Object value) {
        return apply(property -> new SingleCriterionFunction(property, LogicOperation.AND, value));
    }

    @Override
    default CriterionTarget<CriterionFunction> or(Object value) {
        return apply(property -> new SingleCriterionFunction(property, LogicOperation.OR, value));
    }

    @Override
    default CriterionTarget<CriterionFunction> xor(Object value) {
        return apply(property -> new SingleCriterionFunction(property, LogicOperation.XOR, value));
    }

    @Override
    default CriterionTarget<CriterionFunction> not() {
        return apply(value -> new SimpleCriterionFunction(value, LogicOperation.NOT));
    }

    @Override
    default CriterionTarget<CriterionFunction> min() {
        return apply(value -> new SimpleCriterionFunction(value, MathOperation.MIN));
    }

    @Override
    default CriterionTarget<CriterionFunction> max() {
        return apply(value -> new SimpleCriterionFunction(value, MathOperation.MAX));
    }

    @Override
    default CriterionTarget<CriterionFunction> sum() {
        return apply(value -> new SimpleCriterionFunction(value, MathOperation.SUM));
    }

    @Override
    default CriterionTarget<CriterionFunction> avg() {
        return apply(value -> new SimpleCriterionFunction(value, MathOperation.AVG));
    }

    @Override
    default Order asc() {
        return Order.asc(this);
    }

    @Override
    default Order desc() {
        return Order.desc(this);
    }


    interface Builder<T> extends org.finalframework.core.Builder<QProperty<T>> {

        Builder<T> path(String path);

        Builder<T> name(String name);

        Builder<T> column(String column);

        Builder<T> idProperty(boolean idProperty);

        Builder<T> writeable(boolean writeable);

        Builder<T> modifiable(boolean modifiable);

        Builder<T> persistentType(PersistentType persistentType);

        Builder<T> typeHandler(Class<? extends TypeHandler<?>> typeHandler);

        Builder<T> views(List<Class<?>> views);

        Builder<T> insertable(boolean insertable);

        Builder<T> updatable(boolean updatable);

        Builder<T> selectable(boolean selectable);

    }
}
