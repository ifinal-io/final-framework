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

package org.finalframework.data.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.serializer.PropertyJsonSerializer;
import org.springframework.data.mapping.PersistentProperty;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 10:52
 * @since 1.0
 */
@JsonSerialize(using = PropertyJsonSerializer.class)
public interface Property extends PersistentProperty<Property> {

    Integer getOrder();

    default boolean isEnum() {
        return getType().isEnum();
    }

    /**
     * @return
     * @see Column#name()
     * @see Column#value()
     */
    String getColumn();

    /**
     * @return
     * @see Column#writer()
     */
    String getWriter();

    /**
     * @return
     * @see Column#reader()
     */
    String getReader();

    /**
     * @return true if don't have {@link Transient} annotation and have {@link Default} annotation.
     * @see Default
     */
    boolean isDefault();

    /**
     * @return {@code true} if the {@link Property} annotated by {@link Final} annotation.
     * @see Final
     */
    boolean isFinal();

    boolean isReference();

    /**
     * @return {@code true} if the {@link Property} annotated by {@link Virtual} annotation.
     * @see Virtual
     */
    boolean isVirtual();

    /**
     * @return {@code true} if this {@link Property} is annotated by {@link Sharding} annotation.
     * @see Sharding
     */
    boolean isSharding();

    /**
     * @return {@code true} if this {@link Property} is annotated by {@link ReadOnly} annotation.
     * @see ReadOnly
     */
    boolean isReadOnly();


    /**
     * @return {@code true} if this {@link Property} is annotated by {@link WriteOnly} annotation.
     * @see WriteOnly
     */
    boolean isWriteOnly();

    boolean isKeyword();


    default boolean isWriteable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isDefault();
    }

    default boolean isModifiable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isFinal();
    }


    ReferenceMode getReferenceMode();

    Set<String> getReferenceProperties();

    String getReferenceColumn(Property property);

    Class<?> getJavaType();

    default Object get(@NotNull Object target) {
        try {
            return getRequiredGetter().invoke(target);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    default void set(@NotNull Object target, Object value) {
        try {
            getRequiredSetter().invoke(target, value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    default <A extends Annotation> boolean hasAnnotation(Class<A> ann) {
        return findAnnotation(ann) != null;
    }

    Class<? extends TypeHandler<?>> getTypeHandler();
}
