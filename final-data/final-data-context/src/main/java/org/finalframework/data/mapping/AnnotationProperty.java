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

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.Keyword;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.finalframework.data.annotation.SqlKeyWords;
import org.finalframework.data.query.type.JsonParameterTypeHandler;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.Lazy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:03
 * @since 1.0
 */
public class AnnotationProperty extends AnnotationBasedPersistentProperty<Property> implements Property {


    private final Lazy<String> column = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Assert.isBlank(annotation.name())) return getName();
        return annotation.name();
    });

    private final Lazy<String> writer = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Assert.isBlank(annotation.writer())) return null;
        return annotation.writer();
    });


    private final Lazy<String> reader = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Assert.isBlank(annotation.reader())) return null;
        return annotation.reader();
    });

    private final Lazy<Boolean> isTransient = Lazy.of(isAnnotationPresent(Transient.class) || super.isTransient());
    private final Lazy<Boolean> isDefault = Lazy.of(!isTransient() && isAnnotationPresent(Default.class));
    private final Lazy<Boolean> isFinal = Lazy.of(!isTransient() && isAnnotationPresent(Final.class));
    private final Lazy<Boolean> isReference = Lazy.of(!isTransient() && isAnnotationPresent(Reference.class));
    private final Lazy<Boolean> isVirtual = Lazy.of(!isTransient() && isAnnotationPresent(Virtual.class));
    private final Lazy<Boolean> isSharding = Lazy.of(!isTransient() && isAnnotationPresent(Sharding.class));
    private final Lazy<Boolean> isReadonly = Lazy.of(!isTransient() && isAnnotationPresent(ReadOnly.class));
    private final Lazy<Boolean> isWriteOnly = Lazy.of(!isTransient() && isAnnotationPresent(WriteOnly.class));
    private final Lazy<Boolean> isKeyword = Lazy.of(!isTransient() && (isAnnotationPresent(Keyword.class) || SqlKeyWords.contains(getColumn())));
    private final Lazy<ReferenceMode> referenceMode = Lazy.of(isReference() ? getRequiredAnnotation(Reference.class).mode() : ReferenceMode.SIMPLE);
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


    private final Lazy<Class<?>> javaType = Lazy.of(() -> {
        if (isMap()) {
            return Map.class;
        }
        if (isCollectionLike()) {
            return getComponentType();
        }
        return getType();
    });

    private final Lazy<Class<? extends TypeHandler<?>>> typeHandler = Lazy.of(() -> {
        if (isAnnotationPresent(org.finalframework.data.annotation.TypeHandler.class)) {
            return getRequiredAnnotation(org.finalframework.data.annotation.TypeHandler.class).value();
        }

        if (isAnnotationPresent(Json.class)) {
            return JsonParameterTypeHandler.class;
        }

        if (isCollectionLike()) {
            return JsonParameterTypeHandler.class;
        }

        return null;

    });


    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     *
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public AnnotationProperty(org.springframework.data.mapping.model.Property property, org.finalframework.data.mapping.Entity owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }


    @Override
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
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
    public String getWriter() {
        return this.writer.getNullable();
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
    public boolean isReference() {
        return isReference.get();
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }

    @Override
    public boolean isSharding() {
        return isSharding.get();
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
    public ReferenceMode getReferenceMode() {
        return referenceMode.get();
    }

    @Override
    public Set<String> getReferenceProperties() {
        return referenceColumns.get().keySet();
    }

    @Override
    public String getReferenceColumn(Property property) {
        return Optional.ofNullable(referenceColumns.get().get(property.getName())).orElse(property.getColumn());
    }

    @Override
    public Class<?> getJavaType() {
        return javaType.get();
    }

    @Override
    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return typeHandler.getNullable();
    }
}
