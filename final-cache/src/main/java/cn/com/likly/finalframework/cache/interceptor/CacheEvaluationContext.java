package cn.com.likly.finalframework.cache.interceptor;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-20 16:48:12
 * @since 1.0
 */
public class CacheEvaluationContext extends MethodBasedEvaluationContext {

    private final Set<String> unavailableVariables = new HashSet<>(1);

    public CacheEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    public void addUnavailableVariable(String name) {
        this.unavailableVariables.add(name);
    }

}
