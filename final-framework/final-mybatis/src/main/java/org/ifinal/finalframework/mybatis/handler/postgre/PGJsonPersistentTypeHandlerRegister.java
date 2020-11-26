package org.ifinal.finalframework.mybatis.handler.postgre;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.mybatis.handler.PersistentTypeHandlerRegister;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class PGJsonPersistentTypeHandlerRegister extends PersistentTypeHandlerRegister {
    @Override
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {

        TypeHandler<T> typeHandler = super.getTypeHandler(javaType, collectionType);

        if (typeHandler == null) {
            typeHandler = (TypeHandler<T>) initTypeHandler(javaType, collectionType);
            registerTypeHandler(javaType, collectionType, typeHandler);
        }
        return typeHandler;
    }

    private <T> TypeHandler<?> initTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {
        if (collectionType == null) return new PGJsonObjectTypeHandler<>(javaType);

        if (List.class.isAssignableFrom(collectionType)) {
            return new PGJsonListTypeHandler<>(javaType);
        }

        if (Set.class.isAssignableFrom(collectionType)) {
            return new PGJsonSetTypeHandler<>(javaType);
        }

        throw new IllegalArgumentException("");

    }
}
