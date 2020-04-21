package org.finalframework.data.mapping;

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


    static String formatPropertyName(@Nullable Property property, @NonNull Property referenceProperty) {
        if (property == null) {
            return referenceProperty.getName();
        }

        return referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE
                ? property.getName()
                : property.getName() + referenceProperty.getName().substring(0, 1).toUpperCase() + referenceProperty.getName().substring(1);
    }

    static String formatColumn(@Nullable Property property, @NonNull Property referenceProperty) {
        String column = null;
        if (property == null) {
            column = referenceProperty.getColumn();
            if (referenceProperty.isKeyword()) {
                column = String.format("`%s`", column);
            }

        } else {
            final String referenceColumn = property.getReferenceColumn(referenceProperty);
            column = referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE ?
                    property.getColumn() : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        if (property == null && referenceProperty.isVirtual()) {
            column = "v_" + column;
        }

        return NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
    }
}
