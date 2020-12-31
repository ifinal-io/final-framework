package org.ifinal.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.data.Json;
import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.data.query.type.JsonParameterTypeHandler;

/**
 * Utils.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class TypeHandlers {

    private TypeHandlers() {
    }

    public static Class<? extends TypeHandler<?>> findTypeHandler(final Property property) {
        if (property.isAnnotationPresent(org.ifinal.finalframework.annotation.data.TypeHandler.class)) {
            return property.getRequiredAnnotation(org.ifinal.finalframework.annotation.data.TypeHandler.class).value();
        }

        if (property.isAnnotationPresent(Json.class)) {
            return JsonParameterTypeHandler.class;
        }

        if (property.isCollectionLike()) {
            return JsonParameterTypeHandler.class;
        }

        return null;

    }

}
