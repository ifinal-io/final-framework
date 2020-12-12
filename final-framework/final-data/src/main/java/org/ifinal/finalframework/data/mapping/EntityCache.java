package org.ifinal.finalframework.data.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class EntityCache {
    private static final EntityCache ourInstance = new EntityCache();
    private static final Map<Class<?>, Entity<?>> cache = new ConcurrentHashMap<>(1024);

    private EntityCache() {
    }

    public static EntityCache getInstance() {
        return ourInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> Entity<T> get(final Class<T> entity) {

        if (!cache.containsKey(entity)) {
            synchronized (EntityCache.class) {
                cache.put(entity, new AnnotationEntity<>(entity));
            }
        }

        return (Entity<T>) cache.get(entity);
    }
}
