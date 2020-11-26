package org.ifinal.finalframework.mybatis.handler;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.data.PersistentType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class TypeHandlerModule {

    public static final TypeHandlerModule DEFAULT = new TypeHandlerModule() {
        {
            registerPersistentTypeHandlerRegister(PersistentType.AUTO, PersistentTypeHandlerRegister.AUTO);
            registerPersistentTypeHandlerRegister(PersistentType.JSON, new JsonPersistentTypeHandlerRegister());
        }
    };
    private final Map<PersistentType, PersistentTypeHandlerRegister> typeHandlerRegisterMap = new ConcurrentHashMap<>();

    public <T> void registerTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType, TypeHandler<?> typeHandler) {
        PersistentTypeHandlerRegister persistentTypeHandlerRegister = getPersistentTypeHandlerRegister(persistentType);
        if (persistentTypeHandlerRegister != null) {
            persistentTypeHandlerRegister.registerTypeHandler(javaType, collectionType, typeHandler);
        }
    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType) {
        PersistentTypeHandlerRegister persistentTypeHandlerRegister = getPersistentTypeHandlerRegister(persistentType);
        if (persistentTypeHandlerRegister != null) {
            return persistentTypeHandlerRegister.getTypeHandler(javaType, collectionType);
        }
        return null;
    }

    public PersistentTypeHandlerRegister getPersistentTypeHandlerRegister(PersistentType type) {
        return typeHandlerRegisterMap.get(type);
    }

    public void registerPersistentTypeHandlerRegister(PersistentType type, PersistentTypeHandlerRegister register) {
        typeHandlerRegisterMap.put(type, register);
    }

}
