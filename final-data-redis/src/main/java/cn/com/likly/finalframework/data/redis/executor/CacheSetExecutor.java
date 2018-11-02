package cn.com.likly.finalframework.data.redis.executor;

import cn.com.likly.finalframework.data.redis.Cache;
import cn.com.likly.finalframework.data.redis.CacheAnnotationParser;
import cn.com.likly.finalframework.data.redis.CacheOperation;
import cn.com.likly.finalframework.data.redis.annotation.CacheSet;
import cn.com.likly.finalframework.data.redis.parser.CacheSetAnnotationParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 13:33
 * @since 1.0
 */
public class CacheSetExecutor extends BaseCacheExecutor<CacheSet> {
    private final CacheAnnotationParser<CacheSet> cacheAnnotationParser = new CacheSetAnnotationParser();
    @Resource
    private Cache cache;


    @Override
    public Object execute(ProceedingJoinPoint point, CacheSet cacheSet) throws Throwable {

        final Method method = ((MethodSignature) point.getSignature()).getMethod();
        final Class returnType = ((MethodSignature) point.getSignature()).getReturnType();
        final Object argsContext = buildArgsContext(method, point.getArgs());
        CacheOperation cacheOperation = cacheAnnotationParser.parseCacheAnnotation(cacheSet);

        final String key = getKey(cacheOperation, argsContext);
        final Object resultFormCache = cache.get(key, returnType);

        if (resultFormCache != null) return resultFormCache;

        final Object result = point.proceed();
        if (isMatchCondition(cacheOperation, result)) {
            final long expired = getExpired(cacheOperation, result);
            if (expired > 0) {
                cache.set(key, result, expired, cacheOperation.getTimeUnit());
            } else {
                cache.set(key, result);
            }
        }
        return result;
    }
}
