package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
@Slf4j
public class CacheDelOperationInvocation implements CacheOperationInvocation<CacheDelOperation> {
    @Override
    @SuppressWarnings("all")
    public Object invoke(CacheOperationInvocationContext<CacheDelOperation> context, CacheOperationInvoker invoker) throws Throwable {
        Cache cache = CacheRegistry.getInstance().getCache(context.operation());

        if (cache == null) {
            logger.warn("cannot find cache for cache operation : {} ", context.operation());
            return invoker.invoke();
        }

        Object key = context.generateKey(null);

        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
        }
        Object field = context.generateField(null);

        Object result = invoker.invoke();

        if (context.isConditionPassing(result)) {
            boolean flag = field == null ? cache.del(key) : cache.hdel(key, field);
            if (flag) {
                logger.info("delete form cache: key={},field={},result={}", key, field, result);
            }
        } else {
            logger.info("cache del condition is not passing,key={},field={},result={},condition={}", key, field, result, context.operation().condition());

        }

        return result;

    }
}
