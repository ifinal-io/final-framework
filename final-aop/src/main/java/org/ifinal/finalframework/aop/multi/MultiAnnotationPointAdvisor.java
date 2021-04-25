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

package org.ifinal.finalframework.aop.multi;

import java.lang.annotation.Annotation;

import org.ifinal.finalframework.aop.AbsGenericPointcutAdvisor;
import org.ifinal.finalframework.aop.AnnotationBuilder;
import org.ifinal.finalframework.aop.AnnotationSourceMethodPoint;
import org.ifinal.finalframework.aop.DefaultAnnotationMethodInterceptor;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.single.SingleAnnotationSource;
import org.springframework.aop.Pointcut;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class MultiAnnotationPointAdvisor<A, E> extends AbsGenericPointcutAdvisor {

    private Pointcut pointcut;

    private MultiAnnotationSource<A> source = new MultiAnnotationSource<>();

    private MultiValueMap<Class<? extends Annotation>, InterceptorHandler<E, A>> handlers = new LinkedMultiValueMap<>();

    public MultiAnnotationPointAdvisor() {
        this.pointcut = new AnnotationSourceMethodPoint(source);
        this.setAdvice(
            new DefaultAnnotationMethodInterceptor<>(source, new MultiMethodInvocationDispatcher<E, A>(handlers) {
                @Override
                @NonNull
                protected E getExecutor(final A annotation) {

                    return MultiAnnotationPointAdvisor.this.getExecutor(annotation);
                }
            }));
    }

    public <T extends Annotation> void addAnnotation(final Class<T> annotationType,
        final AnnotationBuilder<T, A> builder,
        final InterceptorHandler<E, A> handler) {

        this.source.addAnnotationSource(annotationType, new SingleAnnotationSource<>(annotationType, builder));
        this.handlers.add(annotationType, handler);
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut;
    }

    @NonNull
    protected abstract E getExecutor(A annotation);

}
