package com.ilikly.finalframework.mybatis.handler.postgre;

import com.ilikly.finalframework.mybatis.handler.JsonListTypeHandler;
import com.ilikly.finalframework.mybatis.handler.JsonObjectTypeHandler;
import com.ilikly.finalframework.mybatis.handler.JsonSetTypeHandler;
import com.ilikly.finalframework.mybatis.handler.PersistentTypeHandlerRegister;
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
public class PGJsonPersistentTypeHandlerRegister extends PersistentTypeHandlerRegister {
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
        if(collectionType == null) return new PGJsonObjectTypeHandler<>(javaType);

        if (List.class.isAssignableFrom(collectionType)) {
            return new PGJsonListTypeHandler<>(javaType);
        }

        if (Set.class.isAssignableFrom(collectionType)) {
            return  new PGJsonSetTypeHandler<>(javaType);
        }

        throw new IllegalArgumentException("");

    }
}
