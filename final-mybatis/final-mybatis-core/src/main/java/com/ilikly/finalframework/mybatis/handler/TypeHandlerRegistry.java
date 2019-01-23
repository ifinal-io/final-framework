package com.ilikly.finalframework.mybatis.handler;

import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.data.mapping.Dialect;
import com.ilikly.finalframework.mybatis.handler.postgre.PGTypeHandlerModule;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 13:01
 * @since 1.0
 */
public class TypeHandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TypeHandlerRegistry.class);
    private final Map<Dialect, TypeHandlerModule> typeHandlerModuleMap = new ConcurrentHashMap<>();
    private static final TypeHandlerRegistry INSTANCE = new TypeHandlerRegistry();

    public static TypeHandlerRegistry getInstance() {
        return INSTANCE;
    }



    private TypeHandlerRegistry() {
    }


    {
        registerTypeHandlerModule(Dialect.DEFAULT, TypeHandlerModule.DEFAULT);
        registerTypeHandlerModule(Dialect.POST_GRE, PGTypeHandlerModule.DEFAULT);
    }

    public void registerTypeHandlerModule(Dialect dialect, TypeHandlerModule module) {
        typeHandlerModuleMap.put(dialect, module);
    }

    public <T> TypeHandler<T> getTypeHandler(Dialect dialect, Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType) {

        if(javaType.isEnum()){
            return new EnumEntityTypeHandler(javaType);
        }


        TypeHandlerModule typeHandlerModule = typeHandlerModuleMap.get(dialect);
        if (typeHandlerModule == null) {
            typeHandlerModule = typeHandlerModuleMap.get(Dialect.DEFAULT);
        }

        TypeHandler<T> typeHandler = typeHandlerModule.getTypeHandler(javaType, collectionType, persistentType);
        logger.trace("find typeHandler: dialect={},javaType={},collectionType={},persistentType={},typeHandler={}",
                dialect,javaType,collectionType,persistentType.getClass());
        return typeHandler;

    }


}
