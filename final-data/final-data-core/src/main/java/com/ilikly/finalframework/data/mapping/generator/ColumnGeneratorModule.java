package com.ilikly.finalframework.data.mapping.generator;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 09:52:51
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class ColumnGeneratorModule {

    private final Map<Class<?>, ColumnGenerator> objectColumnGeneratorMap = new ConcurrentHashMap<>();
    private final Map<Class<? extends Collection>, Map<Class<?>, ColumnGenerator>> collectionColumnGeneratorMap = new ConcurrentHashMap<>();

    public <T> void registerColumnGenerator(Class<T> javaType, Class<? extends Collection> collectionType, ColumnGenerator columnGenerator) {
        if (collectionType == null) {
            objectColumnGeneratorMap.put(javaType, columnGenerator);
        } else {
            if (!collectionColumnGeneratorMap.containsKey(collectionType)) {
                collectionColumnGeneratorMap.put(collectionType, new ConcurrentHashMap<>());
            }
            Map<Class<?>, ColumnGenerator> columnGeneratorMap = collectionColumnGeneratorMap.get(collectionType);
            columnGeneratorMap.put(javaType, columnGenerator);
        }
    }

    public <T> ColumnGenerator getColumnGenerator(Class<T> javaType, Class<? extends Collection> collectionType) {
        if (collectionType == null) {
            return objectColumnGeneratorMap.get(javaType);
        } else {
            if (!collectionColumnGeneratorMap.containsKey(collectionType)) {
                collectionColumnGeneratorMap.put(collectionType, new ConcurrentHashMap<>());
            }
            Map<Class<?>, ColumnGenerator> columnGeneratorMap = collectionColumnGeneratorMap.get(collectionType);
            return columnGeneratorMap.get(javaType);
        }
    }


}
