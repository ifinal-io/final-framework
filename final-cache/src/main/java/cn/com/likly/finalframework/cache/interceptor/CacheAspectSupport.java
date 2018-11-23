package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.CacheOperation;
import cn.com.likly.finalframework.cache.CacheOperationExpressionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 18:00:41
 * @since 1.0
 */
@Slf4j
public abstract class CacheAspectSupport<O extends CacheOperation> {
    private final Map<CacheOperationCacheKey, CacheOperationMetadata<O>> metadataCache = new ConcurrentHashMap<>(1024);
    private final CacheOperationExpressionEvaluator evaluator = new DefaultCacheOperationExpressionEvaluator();

    protected CacheOperationContext<O> getCacheOperationContext(O operation, Method method, Object[] args, Object target) {
        CacheOperationMetadata<O> metadata = getCacheOperationMetadata(operation, method, getTargetClass(target));
        return new CacheOperationContext<>(evaluator, metadata, target, args);
    }

    protected CacheOperationMetadata<O> getCacheOperationMetadata(O operation, Method method, Class<?> targetClass) {
        CacheOperationCacheKey cacheKey = new CacheOperationCacheKey(operation, method, targetClass);
        CacheOperationMetadata<O> metadata = this.metadataCache.get(cacheKey);
        if (metadata == null) {
            metadata = new CacheOperationMetadata<>(operation, method, targetClass);
            this.metadataCache.put(cacheKey, metadata);
        }
        return metadata;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

}
