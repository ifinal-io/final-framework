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

import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.web.annotation.servlet.Interceptor;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Servlet;
import java.util.List;
import java.util.function.Predicate;

/**
 * A {@link WebMvcConfigurer} could auto-detects {@link HandlerInterceptor}s witch annotated by {@link Component}.
 *
 * <p>Warn: Don't use {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport}</p>
 *
 * @author ilikly
 * @version 1.0.0
 * @see Interceptor
 * @see HandlerInterceptor
 * @see HandlerInterceptorCustomizer
 * @see WebMvcConfigurer#addInterceptors(InterceptorRegistry)
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class, HandlerInterceptorCustomizer.class})
public class HandlerInterceptorWebMvcConfigurer implements WebMvcConfigurer {

    private final List<HandlerInterceptor> handlerInterceptors;
    private final List<HandlerInterceptorCustomizer> handlerInterceptorCustomizers;

    public HandlerInterceptorWebMvcConfigurer(
            final ObjectProvider<List<HandlerInterceptor>> handlerInterceptorsObjectProvider,
            final ObjectProvider<List<HandlerInterceptorCustomizer>> handlerInterceptorCustomizersProvider) {
        this.handlerInterceptors = handlerInterceptorsObjectProvider.getIfAvailable();
        this.handlerInterceptorCustomizers = handlerInterceptorCustomizersProvider.getIfAvailable();
    }

    @Override
    public void addInterceptors(final @NonNull InterceptorRegistry registry) {
        handlerInterceptors.stream()
                // filter the interceptor annotated by @Component
                .filter(new ComponentHandlerFilter())
                .forEach(item -> this.addInterceptor(registry, item));

    }

    /**
     * Registration {@link HandlerInterceptor} with {@link InterceptorRegistry} and customize by {@link
     * HandlerInterceptorCustomizer}.
     *
     * @param registry    interceptor registry.
     * @param interceptor handler interceptor.
     * @see HandlerInterceptorCustomizer
     */
    private void addInterceptor(@NonNull InterceptorRegistry registry,
                                @NonNull HandlerInterceptor interceptor) {

        final Class<?> targetClass = AopUtils.getTargetClass(interceptor);

        logger.info("==> add interceptor={}", targetClass);

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);

        if (!CollectionUtils.isEmpty(handlerInterceptorCustomizers)) {
            for (HandlerInterceptorCustomizer customizer : handlerInterceptorCustomizers) {
                customizer.customize(interceptorRegistration, interceptor);
            }
        }

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
