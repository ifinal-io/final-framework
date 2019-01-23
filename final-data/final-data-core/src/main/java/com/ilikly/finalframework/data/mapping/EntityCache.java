package com.ilikly.finalframework.data.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-20 15:15:01
 * @since 1.0
 */
final class EntityCache {
    private static final EntityCache ourInstance = new EntityCache();
    private static final Map<Class, Entity> cache = new ConcurrentHashMap<>(1024);

    private EntityCache() {
    }

    public static EntityCache getInstance() {
        return ourInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> Entity<T> get(Class<T> entity) {
        if (!cache.containsKey(entity)) {
            synchronized (EntityCache.class) {
                cache.put(entity, new BaseEntity(entity));
            }
        }

        return cache.get(entity);
    }
}
