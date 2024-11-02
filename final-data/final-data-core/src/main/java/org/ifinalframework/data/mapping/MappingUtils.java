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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.data.annotation.Prefix;
import org.ifinalframework.data.annotation.SqlKeyWords;
import org.ifinalframework.data.annotation.UpperCase;
import org.ifinalframework.data.mapping.converter.NameConverterRegistry;

import java.util.Optional;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MappingUtils {

    static String formatPropertyName(final @NonNull Property property, final @Nullable Property referenceProperty) {

        if (referenceProperty == null) {
            return property.getName();
        }

        return property.getName() + referenceProperty.getName().substring(0, 1).toUpperCase() + referenceProperty
                .getName().substring(1);
    }

    static String formatColumn(final Entity<?> entity, final @NonNull Property property,
                               final @Nullable Property referenceProperty) {

        String column = null;
        if (referenceProperty == null) {
            column = property.getColumn();
            if (property.isKeyword()) {
                column = String.format("`%s`", column);
            }

        } else {
            final String referenceColumn = property.getReferenceColumn(referenceProperty);
            column = property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        if (SqlKeyWords.isKeyWord(column)) {
            column = String.format("`%s`", column);
        }

        if (property.isAnnotationPresent(Prefix.class)) {
            column = property.getRequiredAnnotation(Prefix.class).value() + column;
        }

        column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);

        if (Optional.ofNullable(referenceProperty).orElse(property).isAnnotationPresent(UpperCase.class) || entity
                .isAnnotationPresent(UpperCase.class)) {
            column = column.toUpperCase();
        }
        return column;
    }

}
