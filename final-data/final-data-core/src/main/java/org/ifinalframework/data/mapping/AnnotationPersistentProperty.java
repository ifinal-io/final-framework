/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.data.mapping;

import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;

import org.ifinalframework.core.lang.Default;
import org.ifinalframework.core.lang.Final;
import org.ifinalframework.core.lang.Transient;
import org.ifinalframework.data.annotation.Column;
import org.ifinalframework.data.annotation.Keyword;
import org.ifinalframework.data.annotation.ReadOnly;
import org.ifinalframework.data.annotation.Reference;
import org.ifinalframework.data.annotation.SqlKeyWords;
import org.ifinalframework.data.annotation.Virtual;
import org.ifinalframework.data.annotation.WriteOnly;
import org.ifinalframework.data.mapping.converter.NameConverterRegistry;
import org.ifinalframework.util.Asserts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple implementation of {@link Property}
 *
 * @author iimik
 * @version 1.0.0
 * @see AnnotationBasedPersistentProperty
 * @since 1.0.0
 */
public class AnnotationPersistentProperty extends AnnotationBasedPersistentProperty<Property> implements Property {
    /**
     * @see Column#value()
     * @see Column#name()
     */
    private final Lazy<String> column;

    /**
     * @see Column#insert()
     */
    private final Lazy<String> insert;

    private final Lazy<String> update;

    /**
     * @see Column#select()
     */
    private final Lazy<String> reader;

    private final Lazy<Integer> order;

    private final Lazy<Boolean> isTransient = Lazy.of(super.isTransient() || isAnnotationPresent(Transient.class));

    /**
     * @see Default
     */
    private final Lazy<Boolean> isDefault = Lazy.of(!isTransient() && isAnnotationPresent(Default.class));

    /**
     * @see Final
     */
    private final Lazy<Boolean> isFinal = Lazy.of(!isTransient() && isAnnotationPresent(Final.class));

    /**
     * @see Virtual
     */
    private final Lazy<Boolean> isVirtual = Lazy.of(!isTransient() && isAnnotationPresent(Virtual.class));

    /**
     * @see ReadOnly
     */
    private final Lazy<Boolean> isReadonly = Lazy.of(!isTransient() && isAnnotationPresent(ReadOnly.class));

    /**
     * @see WriteOnly
     */
    private final Lazy<Boolean> isWriteOnly = Lazy.of(!isTransient() && isAnnotationPresent(WriteOnly.class));

    /**
     * @see Keyword
     */
    private final Lazy<Boolean> isKeyword;

    private final Lazy<Map<String, String>> referenceColumns = Lazy.of(() -> {

        Map<String, String> map = new HashMap<>();

        if (isReference()) {
            final Reference reference = getRequiredAnnotation(Reference.class);
            for (String property : reference.properties()) {
                if (property.contains(reference.delimiter())) {
                    final String[] split = property.split(reference.delimiter());
                    map.put(split[0], split[1]);
                } else {
                    map.put(property, null);
                }
            }
        }

        return map;
    });


    public AnnotationPersistentProperty(org.springframework.data.mapping.model.Property property,
                                        PersistentEntity<?, Property> owner, SimpleTypeHolder simpleTypeHolder,
                                        Environment environment) {
        super(property, owner, simpleTypeHolder);

        this.column = Lazy.of(() -> {
            final Column annotation = findAnnotation(Column.class);
            if (annotation == null || Asserts.isBlank(annotation.name())) {
                return getName();
            }
            return environment.resolvePlaceholders(annotation.name());

        });

        this.insert = Lazy.of(() -> {
            final Column annotation = findAnnotation(Column.class);
            if (annotation == null || Asserts.isEmpty(annotation.insert())) {
                return null;
            }
            return Arrays.stream(annotation.insert()).map(String::trim).collect(Collectors.joining());
        });

        this.update = Lazy.of(() -> {
            final Column annotation = findAnnotation(Column.class);
            if (annotation == null || Asserts.isEmpty(annotation.update())) {
                return null;
            }
            return Arrays.stream(annotation.update()).map(String::trim).collect(Collectors.joining());
        });

        this.reader = Lazy.of(() -> {
            final Column annotation = findAnnotation(Column.class);
            if (annotation == null || Asserts.isBlank(annotation.select())) {
                return null;
            }
            return annotation.select();
        });

        this.order = Lazy.of(isAnnotationPresent(Order.class) ? getRequiredAnnotation(Order.class).value() : 0);


        this.isKeyword = Lazy.of(!isTransient() && (isAnnotationPresent(Keyword.class) || SqlKeyWords.contains(getColumn())));

    }


    @Override
    @NonNull
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
    }

    @Override
    public int getOrder() {
        return order.get();
    }

    @Override
    public boolean isTransient() {
        return isTransient.get();
    }

    @Override
    public String getColumn() {
        return NameConverterRegistry.getInstance().getColumnNameConverter().convert(column.get());
    }

    @Override
    public String getInsert() {
        return this.insert.getNullable();
    }

    @Override
    public String getUpdate() {
        return this.update.getNullable();
    }

    @Override
    public String getReader() {
        return this.reader.getNullable();
    }

    @Override
    public boolean isDefault() {
        return isDefault.get();
    }

    @Override
    public boolean isFinal() {
        return isFinal.get();
    }

    @Override
    public boolean isImmutable() {
        return isFinal();
    }

    @Override
    public boolean isReference() {
        return isAssociation();
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }

    @Override
    public boolean isReadOnly() {
        return isReadonly.get();
    }

    @Override
    public boolean isWriteOnly() {
        return isWriteOnly.get();
    }

    @Override
    public boolean isKeyword() {
        return isKeyword.get();
    }

    @Override
    public boolean isIdProperty() {
        return super.isIdProperty() || "id".equals(getName());
    }


    @Override
    public Set<String> getReferenceProperties() {
        return referenceColumns.get().keySet();
    }

    @Override
    public String getReferenceColumn(final Property property) {
        return Optional.ofNullable(referenceColumns.get().get(property.getName())).orElse(property.getColumn());
    }

    @Override
    public Type getGenericType() {
        Field field = getField();
        if (Objects.nonNull(field)) {
            return field.getGenericType();
        }

        Method getter = getGetter();
        if (Objects.nonNull(getter)) {
            return getter.getGenericReturnType();
        }

        Method setter = getSetter();
        if (Objects.nonNull(setter)) {
            return setter.getGenericParameterTypes()[0];
        }

        return null;
    }

}
