package com.ilikly.finalframework.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 22:28:00
 * @since 1.0
 */
@Slf4j
@SuppressWarnings("unchecked")
public final class CacheRegistry {

    private static final String DEFAULT_CACHE_NAME = "default";

    private static final CacheRegistry instance = new CacheRegistry();
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();



    private CacheRegistry() {
    }

    public static CacheRegistry getInstance() {
        return instance;
    }


    public void registerCache(String name, Cache cache) {
        caches.put(name == null ? DEFAULT_CACHE_NAME : name, cache);
    }

    public Cache getCache(CacheOperation operation) {
        return caches.get(DEFAULT_CACHE_NAME);
    }

}
