package com.ilikly.finalframework.mybatis.handler;

import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 13:01
 * @since 1.0
 */
public class TypeHandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TypeHandlerRegistry.class);
    private final TypeHandlerModule typeHandlerModule = new TypeHandlerModule();
    private static final TypeHandlerRegistry INSTANCE = new TypeHandlerRegistry();

    public static TypeHandlerRegistry getInstance() {
        return INSTANCE;
    }



    private TypeHandlerRegistry() {
    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType) {
        if(javaType.isEnum()){
            return new EnumEntityTypeHandler(javaType);
        }

        TypeHandler<T> typeHandler = typeHandlerModule.getTypeHandler(javaType, collectionType, persistentType);
        logger.trace("find typeHandler: ,javaType={},collectionType={},persistentType={},typeHandler={}",
                javaType, collectionType, persistentType.getClass());
        return typeHandler;

    }


}
