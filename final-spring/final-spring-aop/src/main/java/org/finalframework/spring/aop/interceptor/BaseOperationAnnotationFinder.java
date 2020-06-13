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


import org.finalframework.spring.aop.OperationAnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:46:05
 * @since 1.0
 */
public class BaseOperationAnnotationFinder<A extends Annotation> implements OperationAnnotationFinder<A> {

    private final Class<A> ann;
    private final boolean repeatable;

    private BaseOperationAnnotationFinder(Class<A> ann, boolean repeatable) {
        this.ann = ann;
        this.repeatable = repeatable;
    }

    public BaseOperationAnnotationFinder(Class<A> ann) {
        this.ann = ann;
        this.repeatable = ann.getAnnotation(Repeatable.class) != null;
    }

    @Override
    public Collection<A> findOperationAnnotation(Class<?> type) {
        return findOperationAnnotation((AnnotatedElement) type);
    }

    @Override
    public Collection<A> findOperationAnnotation(Method method) {
        return findOperationAnnotation((AnnotatedElement) method);
    }

    @Override
    public Collection<A> findOperationAnnotation(Parameter parameter) {
        return findOperationAnnotation((AnnotatedElement) parameter);
    }

    private Collection<A> findOperationAnnotation(AnnotatedElement ae) {
        if (repeatable) {
            return AnnotatedElementUtils.findMergedRepeatableAnnotations(ae, ann);
        } else {
            final Collection<A> annotations = AnnotatedElementUtils.findAllMergedAnnotations(ae, ann);
            if (annotations.size() > 1) {
                // More than one annotation found -> local declarations override interface-declared ones...
                return AnnotatedElementUtils.getAllMergedAnnotations(ae, ann);
            }
            return annotations;
        }
    }
}
