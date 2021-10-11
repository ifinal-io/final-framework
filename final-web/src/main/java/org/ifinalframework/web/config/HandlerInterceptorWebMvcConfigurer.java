/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.web.config;

import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.web.annotation.servlet.Interceptor;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.function.Predicate;

/**
 * A {@link WebMvcConfigurer} could auto-detects {@link HandlerInterceptor}s witch annotated by {@link Component}.
 *
 * @author likly
 * @version 1.0.0
 * @see Interceptor
 * @see HandlerInterceptor
 * @see WebMvcConfigurer#addInterceptors(InterceptorRegistry)
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class HandlerInterceptorWebMvcConfigurer implements WebMvcConfigurer {

    private final List<HandlerInterceptor> handlerInterceptors;

    public HandlerInterceptorWebMvcConfigurer(
            final ObjectProvider<List<HandlerInterceptor>> handlerInterceptorsObjectProvider) {
        this.handlerInterceptors = handlerInterceptorsObjectProvider.getIfAvailable();
    }

    @Override
    public void addInterceptors(final @NonNull InterceptorRegistry registry) {
        handlerInterceptors.stream()
                // filter the interceptor annotated by @Component
                .filter(new ComponentHandlerFilter())
                .forEach(item -> this.addInterceptor(registry, item));

    }

    private void addInterceptor(@NonNull InterceptorRegistry registry,
                                @NonNull HandlerInterceptor interceptor) {

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);

        final Class<?> targetClass = AopUtils.getTargetClass(interceptor);

        // find @Interceptor
        final Interceptor annotation = AnnotationUtils.getAnnotation(targetClass, Interceptor.class);
        if (annotation != null) {
            if (annotation.includes().length > 0) {
                interceptorRegistration.addPathPatterns(annotation.includes());
            }
            if (annotation.excludes().length > 0) {
                interceptorRegistration.excludePathPatterns(annotation.excludes());
            }
        }

        if (registry instanceof Ordered) {
            interceptorRegistration.order(((Ordered) registry).getOrder());
        } else {
            // find @Order
            final Order order = AnnotationUtils.getAnnotation(targetClass, Order.class);
            if (order != null) {
                interceptorRegistration.order(order.value());
            }
        }

        logger.info("==> add interceptor={}", targetClass);
    }

    /**
     * Find the {@link HandlerInterceptor} with is annotated by {@link Component}.
     */
    private static class ComponentHandlerFilter implements Predicate<HandlerInterceptor> {

        @Override
        public boolean test(final HandlerInterceptor handlerInterceptor) {
            return AnnotatedElementUtils.isAnnotated(AopUtils.getTargetClass(handlerInterceptor), Component.class);
        }

    }

}
