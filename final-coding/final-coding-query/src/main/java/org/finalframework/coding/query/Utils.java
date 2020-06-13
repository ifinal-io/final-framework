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

import org.finalframework.coding.entity.Property;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-21 13:36:27
 * @since 1.0
 */
public interface Utils {

    static String formatPropertyName(@Nullable Property referenceProperty, @NonNull Property property) {
        if (referenceProperty == null) {
            return property.getName();
        } else {
            return property.isIdProperty() ?
                    referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                            referenceProperty.getName() : referenceProperty.getName() + property.getName().substring(0, 1).toUpperCase() + property.getName().substring(1)
                    : referenceProperty.getName() + property.getName().substring(0, 1).toUpperCase() + property.getName().substring(1);
        }
    }

    @NonNull
    static String formatPropertyColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        String column = null;
        if (referenceProperty == null) {
            column = property.getColumn();
            if (property.isKeyword()) {
                column = String.format("`%s`", column);
            }

        } else {
            final String referenceColumn = referenceProperty.referenceColumn(property.getName()) != null ?
                    referenceProperty.referenceColumn(property.getName()) : property.getColumn();
            column = property.isIdProperty() && referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                    referenceProperty.getColumn() : referenceProperty.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);


        return column;
    }
}
