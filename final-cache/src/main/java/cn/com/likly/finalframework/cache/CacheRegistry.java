package cn.com.likly.finalframework.cache;

import cn.com.likly.finalframework.cache.annotation.CacheSet;
import cn.com.likly.finalframework.cache.interceptor.CacheSetAnnotationParser;
import cn.com.likly.finalframework.cache.interceptor.CacheSetOperation;
import cn.com.likly.finalframework.cache.interceptor.CacheSetOperationInvocation;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 22:28:00
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public final class CacheRegistry {

    private static final String DEFAULT_CACHE_NAME = "default";

    private static final CacheRegistry instance = new CacheRegistry();
    private final Map<Class<? extends Annotation>, CacheAnnotationParser<?, ? extends CacheOperation>> annParserCache = new ConcurrentHashMap<>();
    private final Map<Class<? extends CacheOperation>, CacheOperationInvocation<?>> operationInvocationCache = new ConcurrentHashMap<>();
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();

    {
        registerCacheAnnotationParser(CacheSet.class, new CacheSetAnnotationParser());
        registerCacheOperationInvocation(CacheSetOperation.class, new CacheSetOperationInvocation());
    }

    private CacheRegistry() {
    }

    public static CacheRegistry getInstance() {
        return instance;
    }

    public <A extends Annotation> void registerCacheAnnotationParser(Class<A> annClass, CacheAnnotationParser<A, ? extends CacheOperation<A>> parser) {
        annParserCache.put(annClass, parser);
    }

    public <A extends Annotation, O extends CacheOperation<A>> CacheAnnotationParser<A, O> getCacheAnnotationParser(Class<A> annClass) {
        if (!annParserCache.containsKey(annClass)) throw new UnsupportedOperationException("");
        return (CacheAnnotationParser<A, O>) annParserCache.get(annClass);
    }

    public <O extends CacheOperation> void registerCacheOperationInvocation(Class<O> operationClass, CacheOperationInvocation<O> invocation) {
        operationInvocationCache.put(operationClass, invocation);
    }

    public <O extends CacheOperation> CacheOperationInvocation<O> getCacheOperationInvocation(Class<O> operationClass) {
        if (!operationInvocationCache.containsKey(operationClass)) throw new UnsupportedOperationException("");
        return (CacheOperationInvocation<O>) operationInvocationCache.get(operationClass);
    }

    public void registerCache(String name, Cache cache) {
        caches.put(name == null ? DEFAULT_CACHE_NAME : name, cache);
    }

    public Cache getCache(CacheOperation operation) {
        return caches.get(DEFAULT_CACHE_NAME);
    }

}
