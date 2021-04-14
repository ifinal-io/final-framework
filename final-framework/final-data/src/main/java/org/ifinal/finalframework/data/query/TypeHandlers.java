package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.data.annotation.Json;
import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.data.query.type.JsonParameterTypeHandler;

import org.apache.ibatis.type.TypeHandler;

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
        if (property.isAnnotationPresent(org.ifinal.finalframework.data.annotation.TypeHandler.class)) {
            return property.getRequiredAnnotation(org.ifinal.finalframework.data.annotation.TypeHandler.class).value();
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
