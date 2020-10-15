package org.finalframework.context.expression;


import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-21 22:44:11
 * @since 1.0
 */
public class MethodEvaluationContext extends MethodBasedEvaluationContext {

    private final Set<String> unavailableVariables = new HashSet<>(1);

    public MethodEvaluationContext(MethodExpressionRootObject rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    public void addUnavailableVariable(String name) {
        this.unavailableVariables.add(name);
    }

}
