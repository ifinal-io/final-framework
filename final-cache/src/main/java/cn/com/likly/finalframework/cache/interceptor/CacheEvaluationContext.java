package cn.com.likly.finalframework.cache.interceptor;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-20 16:48:12
 * @since 1.0
 */
public class CacheEvaluationContext extends MethodBasedEvaluationContext {
    public CacheEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }
}
