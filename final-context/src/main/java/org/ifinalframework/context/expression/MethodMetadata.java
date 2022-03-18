/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.context.expression;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import lombok.Getter;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class MethodMetadata {

    private final Method method;

    private final Class<?> returnType;

    private final Type genericReturnType;

    private final Class<?> targetClass;

    private final Method targetMethod;

    private final AnnotatedElementKey methodKey;

    public MethodMetadata(final Method method, final Class<?> targetClass) {

        this.method = BridgeMethodResolver.findBridgedMethod(method);
        this.returnType = method.getReturnType();
        this.genericReturnType = method.getGenericReturnType();
        this.targetClass = targetClass;
        this.targetMethod = !Proxy.isProxyClass(targetClass)
            ? AopUtils.getMostSpecificMethod(method, targetClass) : this.method;
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }

}
