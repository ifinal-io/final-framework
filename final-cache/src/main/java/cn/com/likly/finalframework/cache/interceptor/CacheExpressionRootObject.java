package cn.com.likly.finalframework.cache.interceptor;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-20 16:46:47
 * @since 1.0
 */
@Data
public class CacheExpressionRootObject {
    private final Method method;
    private final Object[] args;
    private final Object target;
    private final Class<?> targetClass;

    public CacheExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
    }
}
