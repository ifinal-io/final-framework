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

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationAttributesAnnotationBuilder<A extends Annotation> implements
        AnnotationBuilder<A, AnnotationAttributes> {

    @Override
    @NonNull
    public AnnotationAttributes build(final @NonNull Class<?> type, final @NonNull A annotation) {

        final AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(type, annotation);
        annotationAttributes.put("class", type);
        return annotationAttributes;
    }

    @Override
    @NonNull
    public AnnotationAttributes build(final @NonNull Method method, final @NonNull A annotation) {

        final AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(method, annotation);
        annotationAttributes.put("method", method);
        return annotationAttributes;
    }

    @Override
    @NonNull
    public AnnotationAttributes build(final @NonNull Parameter parameter, final @NonNull Integer index,
                                      final @NonNull A annotation) {

        final AnnotationAttributes annotationAttributes
                = AnnotationUtils.getAnnotationAttributes(parameter, annotation);
        annotationAttributes.put("parameter", parameter);
        annotationAttributes.put("parameterIndex", index);
        return annotationAttributes;
    }

}
