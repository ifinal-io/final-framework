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

import java.util.Optional;

import org.finalframework.data.annotation.Prefix;
import org.finalframework.data.annotation.UpperCase;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-15 21:46:50
 * @since 1.0
 */
public interface MappingUtils {


    static String formatPropertyName(@NonNull Property property, @Nullable Property referenceProperty) {
        if (referenceProperty == null) {
            return property.getName();
        }

        return referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE
                ? property.getName()
                : property.getName() + referenceProperty.getName().substring(0, 1).toUpperCase() + referenceProperty
                .getName().substring(1);
    }

    static String formatColumn(Entity<?> entity, @NonNull Property property, @Nullable Property referenceProperty) {
        String column = null;
        if (referenceProperty == null) {
            column = property.getColumn();
            if (property.isKeyword()) {
                column = String.format("`%s`", column);
            }

        } else {
            final String referenceColumn = property.getReferenceColumn(referenceProperty);
            column = referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE ?
                    property.getColumn()
                    : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        if (property.isAnnotationPresent(Prefix.class)) {
            column = property.getRequiredAnnotation(Prefix.class).value() + column;
        }

        column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);

        if (Optional.ofNullable(referenceProperty).orElse(property).hasAnnotation(UpperCase.class) || entity
                .isAnnotationPresent(UpperCase.class)) {
            column = column.toUpperCase();
        }
        return column;
    }
}
