package org.ifinal.finalframework.mybatis.handler;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.util.Primaries;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"rawtypes", "unused"})
public class PersistentTypeHandlerRegister {
    public static final PersistentTypeHandlerRegister AUTO = new PersistentTypeHandlerRegister();
    private final Map<Class<?>, TypeHandler<?>> objectTypeHandlerMap = new ConcurrentHashMap<>();
    private final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> collectionTypeHandlerMap = new ConcurrentHashMap<>();

    static {

        Primaries.ALL.forEach(clazz -> AUTO.registerTypeHandler(clazz, List.class, new DefaultListTypeHandler<>(clazz)));
        Primaries.ALL.forEach(clazz -> AUTO.registerTypeHandler(clazz, Set.class, new DefaultSetTypeHandler<>(clazz)));
    }


    public <T> boolean hasTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {
        return getTypeHandler(javaType, collectionType) != null;
    }

    public <T> void registerTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, TypeHandler<?> typeHandler) {
        if (collectionType == null) {
            objectTypeHandlerMap.put(javaType, typeHandler);
        } else {
            if (!collectionTypeHandlerMap.containsKey(collectionType)) {
                collectionTypeHandlerMap.put(collectionType, new ConcurrentHashMap<>());
            }
            collectionTypeHandlerMap.get(collectionType).put(javaType, typeHandler);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {
        if (collectionType == null) {
            return (TypeHandler<T>) objectTypeHandlerMap.get(javaType);
        } else {
            if (!collectionTypeHandlerMap.containsKey(collectionType)) {
                collectionTypeHandlerMap.put(collectionType, new ConcurrentHashMap<>());
            }
            return (TypeHandler<T>) collectionTypeHandlerMap.get(collectionType).get(javaType);
        }
    }
}
