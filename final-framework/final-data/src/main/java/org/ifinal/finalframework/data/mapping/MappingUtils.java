package org.ifinal.finalframework.data.mapping;

import org.ifinal.finalframework.annotation.data.Prefix;
import org.ifinal.finalframework.annotation.data.ReferenceMode;
import org.ifinal.finalframework.annotation.data.UpperCase;
import org.ifinal.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MappingUtils {

    static String formatPropertyName(final @NonNull Property property, final @Nullable Property referenceProperty) {

        if (referenceProperty == null) {
            return property.getName();
        }

        return referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE
                ? property.getName()
                : property.getName() + referenceProperty.getName().substring(0, 1).toUpperCase() + referenceProperty
                .getName().substring(1);
    }

    static String formatColumn(final Entity<?> entity, final @NonNull Property property, final @Nullable Property referenceProperty) {

        String column = null;
        if (referenceProperty == null) {
            column = property.getColumn();
            if (property.isKeyword()) {
                column = String.format("`%s`", column);
            }

        } else {
            final String referenceColumn = property.getReferenceColumn(referenceProperty);
            column = referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE
                    ? property.getColumn()
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
