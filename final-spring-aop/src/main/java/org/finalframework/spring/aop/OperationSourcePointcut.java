

package org.finalframework.spring.aop;


import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-05 13:45:55
 * @since 1.0
 */
public class OperationSourcePointcut extends StaticMethodMatcherPointcut {
    private final OperationSource source;

    public OperationSourcePointcut(OperationSource source) {
        this.source = source;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return source != null && source.matches(method, targetClass);
    }
}

