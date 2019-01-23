package com.ilikly.finalframework.mybatis.handler;

import org.apache.ibatis.type.TypeHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 12:26:05
 * @since 1.0
 */
public class PersistentTypeHandlerRegister {
    private final Map<Class<?>, TypeHandler<?>> objectTypeHandlerMap = new ConcurrentHashMap<>();
    private final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> collectionTypeHandlerMap = new ConcurrentHashMap<>();

    private static List<Class> primaryClass = Arrays.asList(
            byte.class, Byte.class, short.class, Short.class,
            char.class, Character.class, boolean.class, Boolean.class,
            int.class, Integer.class, long.class, Long.class,
            float.class, Float.class, double.class, Double.class,
            String.class
    );

    public static final PersistentTypeHandlerRegister AUTO = new PersistentTypeHandlerRegister(){
        {
            primaryClass.forEach(clazz -> registerTypeHandler(clazz,List.class,new DefaultListTypeHandler<>(clazz)));
            primaryClass.forEach(clazz -> registerTypeHandler(clazz, Set.class,new DefaultSetTypeHandler<>(clazz)));
        }
    };

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

    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {
        if (collectionType == null){
            return (TypeHandler<T>) objectTypeHandlerMap.get(javaType);
        }else {
            if (!collectionTypeHandlerMap.containsKey(collectionType)) {
                collectionTypeHandlerMap.put(collectionType, new ConcurrentHashMap<>());
            }
            return (TypeHandler<T>) collectionTypeHandlerMap.get(collectionType).get(javaType);
        }
    }
}
