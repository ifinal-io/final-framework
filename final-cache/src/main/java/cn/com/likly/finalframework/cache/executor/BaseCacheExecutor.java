package cn.com.likly.finalframework.cache.executor;

import cn.com.likly.finalframework.cache.CacheExecutor;
import cn.com.likly.finalframework.cache.CacheOperation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 14:11
 * @since 1.0
 */
public abstract class BaseCacheExecutor<A extends Annotation> implements CacheExecutor<A> {

    protected Object buildArgsContext(Method method, Object[] args) {
        if (args.length == 1) {
            return args[0];
        }
        final Map<String, Object> result = new LinkedHashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            result.put(parameters[i].getName(), args[i]);
        }
        return result;
    }

    protected String getKey(CacheOperation operation, Object context) {
        return operation.getKey();
    }

    protected String getField(CacheOperation operation, Object context) {
        return operation.getField();
    }

    protected boolean isMatchCondition(CacheOperation operation, Object context) {
        return true;
    }

    protected long getExpired(CacheOperation operation, Object context) {
        return -1;
    }


}
