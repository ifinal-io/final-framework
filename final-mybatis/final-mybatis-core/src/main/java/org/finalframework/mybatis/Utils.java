package org.finalframework.mybatis;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.generator.ColumnGenerator;
import org.finalframework.data.mapping.generator.ColumnGeneratorRegistry;
import org.finalframework.mybatis.generator.DefaultColumnGenerator;
import org.finalframework.mybatis.handler.TypeHandlerRegistry;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:12:38
 * @since 1.0
 */
@SuppressWarnings("all")
public interface Utils {
    static Class getPropertyJavaType(Property property) {
        return property.isMap() || property.isCollectionLike() ? property.getComponentType() : property.getType();
    }

    static Class getPropertyCollectionType(Property property) {
        return property.isCollectionLike() ? property.getType() : null;
    }

    static Class<? extends TypeHandler> getPropertyTypeHandlerClass(Property property) {
        if (property.hasAnnotation(org.finalframework.data.annotation.TypeHandler.class)) {
            return property.findAnnotation(org.finalframework.data.annotation.TypeHandler.class).value();
        }

        final TypeHandler<Object> typeHandler = getPropertyTypeHandler(property);
        return typeHandler == null ? null : typeHandler.getClass();
    }

    static <T> TypeHandler<T> getPropertyTypeHandler(Property property) {
        final Class javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class collectionType = property.isCollectionLike() ? property.getType() : null;
        return TypeHandlerRegistry.getInstance().getTypeHandler(javaType, collectionType, property.getPersistentType());
    }

    static ColumnGenerator getPropertyColumnGenerator(Property property) {
        final Class javaType = getPropertyJavaType(property);
        final Class collectionType = getPropertyCollectionType(property);
        ColumnGenerator columnGenerator = ColumnGeneratorRegistry.getInstance().getColumnGenerator(javaType, collectionType);
        return columnGenerator == null ? DefaultColumnGenerator.INSTANCE : columnGenerator;
    }

}
