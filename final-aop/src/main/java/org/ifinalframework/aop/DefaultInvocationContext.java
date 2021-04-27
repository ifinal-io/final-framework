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

package org.ifinalframework.aop;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import org.ifinalframework.context.expression.MethodMetadata;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultInvocationContext implements InvocationContext {

    private final MethodMetadata metadata;

    private final Object target;

    private final Object[] args;

    private final Class<?> view;

    private final Map<String, Object> attributes = new HashMap<>();

    public DefaultInvocationContext(final MethodMetadata metadata, final Object target, final Object[] args) {

        this.metadata = metadata;
        this.target = target;
        this.args = extractArgs(metadata.getMethod(), args);
        this.view = extractView(metadata.getMethod());
    }

    @Override
    public MethodMetadata metadata() {
        return metadata;
    }

    @Override
    public Object target() {
        return target;
    }

    @Override
    public Object[] args() {
        return args;
    }

    @Override
    public Class<?> view() {
        return view;
    }

    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public void addAttribute(final String name, final Object value) {

        attributes.put(name, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(final String name) {

        return (T) attributes.get(name);
    }

    private Object[] extractArgs(final Method method, final Object[] args) {

        if (!method.isVarArgs()) {
            return args;
        }
        final Object[] varArgs = ObjectUtils.toObjectArray(args[args.length - 1]);
        final Object[] combinedArgs = new Object[args.length - 1 + varArgs.length];
        System.arraycopy(args, 0, combinedArgs, 0, args.length - 1);
        System.arraycopy(varArgs, 0, combinedArgs, args.length - 1, varArgs.length);
        return combinedArgs;
    }

    private Class<?> extractView(final Method method) {

        final JsonView jsonView = AnnotationUtils.findAnnotation(method, JsonView.class);
        if (jsonView == null) {
            return null;
        }
        final Class<?>[] classes = jsonView.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                "@JsonView only supported for cache advice with exactly 1 class argument: " + method.getDeclaringClass()
                    .getCanonicalName() + "#" + method
                    .getName());
        }
        return classes[0];
    }

}
