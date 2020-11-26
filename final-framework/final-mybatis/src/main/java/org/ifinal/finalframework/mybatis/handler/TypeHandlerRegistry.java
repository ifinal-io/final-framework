package org.ifinal.finalframework.mybatis.handler;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.data.PersistentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class TypeHandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TypeHandlerRegistry.class);
    private static final TypeHandlerRegistry INSTANCE = new TypeHandlerRegistry();
    private final TypeHandlerModule typeHandlerModule = TypeHandlerModule.DEFAULT;

    private TypeHandlerRegistry() {
    }

    public static TypeHandlerRegistry getInstance() {
        return INSTANCE;
    }

    public <T> void registerTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType, TypeHandler<?> typeHandler) {
        typeHandlerModule.registerTypeHandler(javaType, collectionType, persistentType, typeHandler);
    }

    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType) {
        if (javaType.isEnum()) {
            return new EnumTypeHandler(javaType);
        }

        TypeHandler<T> typeHandler = typeHandlerModule.getTypeHandler(javaType, collectionType, persistentType);
        logger.trace("find typeHandler: ,javaType={},collectionType={},persistentType={},typeHandler={}",
                javaType, collectionType, persistentType.getClass(), typeHandler == null ? null : typeHandler.getClass().getCanonicalName());
        return typeHandler;

    }

}
