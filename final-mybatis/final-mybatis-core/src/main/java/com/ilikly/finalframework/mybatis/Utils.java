package com.ilikly.finalframework.mybatis;

import com.ilikly.finalframework.data.mapping.Dialect;
import com.ilikly.finalframework.data.mapping.Property;
import com.ilikly.finalframework.data.mapping.generator.ColumnGenerator;
import com.ilikly.finalframework.data.mapping.generator.ColumnGeneratorRegistry;
import com.ilikly.finalframework.mybatis.handler.TypeHandlerRegistry;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:12:38
 * @since 1.0
 */
@SuppressWarnings("all")
public interface Utils {

    static Class getPropertyJavaType(Property property) {
        return property.isCollectionLike() ? property.getComponentType() : property.getType();
    }

    static Class getPropertyCollectionType(Property property) {
        return property.isCollectionLike() ? property.getType() : null;
    }

    static <T> TypeHandler<T> getPropertyTypeHandler(Dialect dialect, Property property) {
        final Class javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class collectionType = property.isCollectionLike() ? property.getType() : null;
        return TypeHandlerRegistry.getInstance().getTypeHandler(dialect, javaType, collectionType, property.getPersistentType());
    }

    static ColumnGenerator getPropertyColumnGenerator(Dialect dialect,Property property) {
        final Class javaType = getPropertyJavaType(property);
        final Class collectionType = getPropertyCollectionType(property);
        return ColumnGeneratorRegistry.getInstance().getColumnGenerator(dialect, javaType, collectionType);
    }

}
