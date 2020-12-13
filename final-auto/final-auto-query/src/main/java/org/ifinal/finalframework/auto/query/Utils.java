package org.ifinal.finalframework.auto.query;

import org.ifinal.finalframework.annotation.data.ReferenceMode;
import org.ifinal.finalframework.auto.data.Property;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Utils {

    static String formatPropertyName(final @Nullable Property referenceProperty, final @NonNull Property property) {

        if (referenceProperty == null) {
            return property.getName();
        } else {

            if (property.isIdProperty()) {
                if (referenceProperty.referenceMode() == ReferenceMode.SIMPLE) {
                    return referenceProperty.getName();
                }

                return referenceProperty.getName() + property.getName().substring(0, 1).toUpperCase() + property
                    .getName().substring(1);
            }

            return referenceProperty.getName() + property.getName().substring(0, 1).toUpperCase() + property.getName()
                .substring(1);
        }
    }

}
