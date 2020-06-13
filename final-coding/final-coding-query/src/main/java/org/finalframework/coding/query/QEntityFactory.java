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

package org.finalframework.coding.query;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.entity.Property;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-26 14:07:01
 * @since 1.0
 */
public class QEntityFactory {

    public static QEntity create(ProcessingEnvironment processingEnv, String packageName, Entity entity) {
        final String entityName = entity.getSimpleName();
        final QEntity.Builder builder = new QEntity.Builder(entity);
        builder.packageName(packageName)
                .name("Q" + entityName);
        entity.stream()
                .filter(property -> !property.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        TypeElement multiElement = property.getJavaTypeElement();
                        Entity multiEntity = EntityFactory.create(processingEnv, multiElement);
                        @SuppressWarnings("unchecked") final List<String> properties = property.referenceProperties();
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


    private static QProperty buildProperty(@Nullable Property referenceProperty, @NonNull Property property) {
        if (referenceProperty == null) {
//            System.out.println("1:" + property.getName() + ":isReference" + property.isReference() + ":column=" + property.getColumn() + ":isIdProperty=" + property.isIdProperty());
            return QProperty.builder(property.getName(), Utils.formatPropertyName(null, property))
                    .type(property.getJavaTypeElement())
//                    .typeHandler(Optional.ofNullable(property.getTypeHandler()).orElse(typeHandlers.getTypeHandler(property)))
                    .idProperty(property.isIdProperty())
                    .column(Utils.formatPropertyColumn(null, property))
                    .persistentType(property.getPersistentType())
                    .insertable(property.isWriteable())
                    .updatable(property.isModifiable())
                    .selectable(!property.isTransient() && !property.isWriteOnly() && !property.isVirtual())
                    .build();
        } else {
//            System.out.println("2:" + property.getName() + ":isReference" + property.isReference() + ":column=" + property.getColumn() + ":isIdProperty=" + property.isIdProperty());
            final String path = referenceProperty.getName() + "." + property.getName();
            final String name = Utils.formatPropertyName(referenceProperty, property);
            return QProperty.builder(path, name)
                    .column(Utils.formatPropertyColumn(referenceProperty, property))
                    .type(property.getJavaTypeElement())
//                    .typeHandler(Optional.ofNullable(property.getTypeHandler()).orElse(typeHandlers.getTypeHandler(property)))
                    .idProperty(false)
                    .persistentType(property.getPersistentType())
                    .insertable(property.isWriteable())
                    .updatable(property.isModifiable())
                    .selectable(!referenceProperty.isTransient() && !referenceProperty.isWriteOnly() && !referenceProperty.isVirtual())
                    .build();
        }

    }
}

