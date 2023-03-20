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

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

/**
 * Customize {@link HandlerInterceptor} with {@link InterceptorRegistration}.
 *
 * @author ilikly
 * @version 1.2.2
 * @see OrderHandlerInterceptorCustomizer
 * @see InterceptorAnnotationHandlerInterceptorCustomizer
 * @since 1.2.2
 */
@FunctionalInterface
public interface HandlerInterceptorCustomizer {
    /**
     * Customize the {@link HandlerInterceptor} with {@link InterceptorRegistration}.
     *
     * @param registration interceptor registration.
     * @param interceptor  handler interceptor.
     */
    void customize(@NonNull InterceptorRegistration registration, @NonNull HandlerInterceptor interceptor);
}
