package org.finalframework.spring.aop.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.finalframework.core.Assert;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationPointCut;
import org.finalframework.spring.aop.OperationSource;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 00:12:36
 * @since 1.0
 */
@Slf4j
public abstract class OperationSourcePoint extends StaticMethodMatcherPointcut implements OperationPointCut, Serializable {

    private static final long serialVersionUID = 1550041602151326776L;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        final OperationSource<? extends Operation> source = getOperationSource();
        return source != null && Assert.nonEmpty(source.getOperations(method, targetClass));
    }

    protected abstract OperationSource<? extends Operation> getOperationSource();
}
