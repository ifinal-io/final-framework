/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.aop.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-04 23:13:36
 * @since 1.0
 */
public class BaseOperationInvocation extends OperationInvocationSupport implements OperationInvocation {

    private final MethodInvocation methodInvocation;

    public BaseOperationInvocation(OperationConfiguration configuration, MethodInvocation methodInvocation) {
        super(configuration);
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Method getMethod() {
        return methodInvocation.getMethod();
    }

    @Override
    public Object[] getArguments() {
        return methodInvocation.getArguments();
    }

    @Override
    public Object proceed() throws Throwable {
        return this.methodInvocation.proceed();
    }

    @Override
    public Object getThis() {
        return methodInvocation.getThis();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return methodInvocation.getStaticPart();
    }

    @Override
    public Collection<OperationContext<Operation>> getOperationContexts() {
        return getOperationContexts(getThis(), getMethod(), getArguments());
    }
}
