/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.web.servlet.config;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import org.ifinalframework.web.annotation.servlet.Interceptor;

/**
 * Parse {@link Interceptor} annotation in {@link HandlerInterceptor}.
 *
 * @author iimik
 * @version 1.2.2
 * @see Interceptor
 * @since 1.2.2
 */
@Component
public class InterceptorAnnotationHandlerInterceptorCustomizer implements HandlerInterceptorCustomizer {
    @Override
    public void customize(@NonNull InterceptorRegistration registration, @NonNull HandlerInterceptor interceptor) {
        final Class<?> targetClass = AopUtils.getTargetClass(interceptor);

        // find @Interceptor
        final Interceptor annotation = AnnotationUtils.getAnnotation(targetClass, Interceptor.class);
        if (annotation != null) {
            if (annotation.includes().length > 0) {
                registration.addPathPatterns(annotation.includes());
            }
            if (annotation.excludes().length > 0) {
                registration.excludePathPatterns(annotation.excludes());
            }
        }
    }
}
