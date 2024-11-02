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

package org.ifinalframework.data.auto.query;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.data.auto.entity.Entity;
import org.ifinalframework.data.auto.entity.EntityFactory;
import org.ifinalframework.data.auto.entity.Property;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class QEntityFactory {

    private static final String ENTITY_PREFIX = "Q";

    private QEntityFactory() {
    }

    public static QEntity create(final ProcessingEnvironment processingEnv, final String packageName,
                                 final Entity entity) {

        final String entityName = entity.getSimpleName();
        final QEntity.Builder builder = new QEntity.Builder(entity);
        builder.packageName(packageName)
                .name(ENTITY_PREFIX + entityName);
        entity.stream()
                .filter(property -> !property.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        TypeElement multiElement = property.getJavaTypeElement();
                        Entity multiEntity = EntityFactory.create(processingEnv, multiElement);
                        final List<String> properties = property.referenceProperties();
                        properties.stream()
                                .map(multiEntity::getRequiredProperty)
                                .map(multiProperty -> buildProperty(property, multiProperty))
                                .forEach(builder::addProperty);
                    } else {
                        builder.addProperty(buildProperty(null, property));
                    }

                });

        return builder.build();
    }

    private static QProperty buildProperty(final @Nullable Property referenceProperty,
                                           final @NonNull Property property) {

        if (referenceProperty == null) {
            return QProperty.builder(property.getName(), Utils.formatPropertyName(null, property))
                    .element(property.getElement())
                    .idProperty(property.isIdProperty())
                    .build();
        } else {
            final String path = referenceProperty.getName() + "." + property.getName();
            final String name = Utils.formatPropertyName(referenceProperty, property);
            return QProperty.builder(path, name)
                    .element(property.getElement())
                    .idProperty(false)
                    .build();
        }

    }

}

