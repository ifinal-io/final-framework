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

package org.ifinalframework.aop.single;

import org.springframework.aop.Pointcut;
import org.springframework.lang.NonNull;

import org.ifinalframework.aop.AbsGenericPointcutAdvisor;
import org.ifinalframework.aop.AnnotationBuilder;
import org.ifinalframework.aop.AnnotationSource;
import org.ifinalframework.aop.AnnotationSourceMethodPoint;
import org.ifinalframework.aop.DefaultAnnotationMethodInterceptor;
import org.ifinalframework.aop.InterceptorHandler;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SingleAnnotationBeanFactoryPointcutAdvisor<A extends Annotation, E, T> extends
        AbsGenericPointcutAdvisor {

    private Pointcut pointcut;

    protected SingleAnnotationBeanFactoryPointcutAdvisor(final Class<A> annotationType,
                                                         final AnnotationBuilder<A, E> builder,
                                                         final List<InterceptorHandler<T, E>> handlers) {

        this(new SingleAnnotationSource<>(annotationType, builder), handlers);
    }

    protected SingleAnnotationBeanFactoryPointcutAdvisor(final AnnotationSource<Collection<E>> source,
                                                         final List<InterceptorHandler<T, E>> handlers) {

        this.pointcut = new AnnotationSourceMethodPoint(source);
        setAdvice(
                new DefaultAnnotationMethodInterceptor<>(source, new SingleMethodInvocationDispatcher<T, E>(handlers) {

                    @Override
                    @NonNull
                    protected T getExecutor(final E annotation) {

                        return SingleAnnotationBeanFactoryPointcutAdvisor.this.getExecutor(annotation);
                    }
                }));
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut;
    }

    @NonNull
    protected abstract T getExecutor(E annotation);

}
