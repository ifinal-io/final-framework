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

package org.finalframework.aop.simple;

import org.springframework.aop.Pointcut;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

import org.finalframework.aop.AbsGenericPointcutAdvisor;
import org.finalframework.aop.AnnotationSourceMethodPoint;
import org.finalframework.aop.DefaultAnnotationMethodInterceptor;
import org.finalframework.aop.InterceptorHandler;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleAnnotationBeanFactoryPointAdvisor<T> extends AbsGenericPointcutAdvisor {

    private final Pointcut pointcut;

    protected SimpleAnnotationBeanFactoryPointAdvisor(final Collection<Class<? extends Annotation>> annotationTypes,
        final List<InterceptorHandler<T, Boolean>> handlers) {

        final SimpleAnnotationSource source = new SimpleAnnotationSource(annotationTypes);
        this.pointcut = new AnnotationSourceMethodPoint(source);
        this.setAdvice(
            new DefaultAnnotationMethodInterceptor<>(source, new SimpleMethodInvocationDispatcher<T>(handlers) {

                @Override
                protected T getExecutor() {

                    return null;
                }
            }));
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut;
    }

    protected abstract T getExecutor();

}
