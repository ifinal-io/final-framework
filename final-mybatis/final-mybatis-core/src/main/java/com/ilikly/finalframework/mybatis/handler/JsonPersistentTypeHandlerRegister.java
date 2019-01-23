package com.ilikly.finalframework.mybatis.handler;

import org.apache.ibatis.type.TypeHandler;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 12:43:08
 * @since 1.0
 */
@SuppressWarnings("all")
public class JsonPersistentTypeHandlerRegister extends PersistentTypeHandlerRegister {
    @Override
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {

        TypeHandler<T> typeHandler = super.getTypeHandler(javaType, collectionType);

        if (typeHandler == null) {
            typeHandler = (TypeHandler<T>) initTypeHandler(javaType,collectionType);
            registerTypeHandler(javaType,collectionType,typeHandler);
        }
        return typeHandler;
    }

    private <T> TypeHandler<?> initTypeHandler(Class<T> javaType,Class<? extends Collection> collectionType){
        if(collectionType == null) return new JsonObjectTypeHandler<>(javaType);

        if (List.class.isAssignableFrom(collectionType)) {
            return new JsonListTypeHandler<>(javaType);
        }

        if (Set.class.isAssignableFrom(collectionType)) {
            return  new JsonSetTypeHandler<>(javaType);
        }

        throw new IllegalArgumentException("");

    }
}
