package org.finalframework.data.mapping;

import org.finalframework.annotation.data.Prefix;
import org.finalframework.annotation.data.ReferenceMode;
import org.finalframework.annotation.data.UpperCase;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

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

        if (Optional.ofNullable(referenceProperty).orElse(property).isAnnotationPresent(UpperCase.class) || entity
                .isAnnotationPresent(UpperCase.class)) {
            column = column.toUpperCase();
        }
        return column;
    }
}
