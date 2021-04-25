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
 *
 */

package org.ifinal.finalframework.context.resource;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import lombok.Getter;

import org.springframework.util.ReflectionUtils;

/**
 * ResourceValueHolder.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public final class ResourceValueHolder {

    private final String key;

    private final Object target;

    private final AnnotatedElement element;

    private final ResourceValueType type;

    private final Type valueType;

    private Object value;

    public ResourceValueHolder(final String key, final Object target, final AnnotatedElement element) {
        this.key = key;
        this.target = target;
        this.element = element;

        if (element instanceof Field) {
            this.type = ResourceValueType.FIELD;
            valueType = ((Field) element).getType();
            ReflectionUtils.makeAccessible((Field) element);
        } else if (element instanceof Method) {
            this.type = ResourceValueType.METHOD;
            ReflectionUtils.makeAccessible((Method) element);
            valueType = ((Method) element).getGenericParameterTypes()[0];
        } else {
            throw new IllegalArgumentException("result value only support filed or method. not support the type of " + element);
        }

    }

    public void setValue(final Object value) {
        switch (type) {
            case FIELD:
                try {
                    ((Field) element).set(target, value);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
                break;
            case METHOD:
                try {
                    ((Method) element).invoke(target, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                }
                break;
            default:
                throw new IllegalArgumentException("");
        }

        this.value = value;
    }

}
