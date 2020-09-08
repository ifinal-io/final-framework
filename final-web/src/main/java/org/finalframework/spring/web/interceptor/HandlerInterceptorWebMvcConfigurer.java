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

package org.finalframework.spring.web.interceptor;

import org.finalframework.auto.spring.factory.annotation.SpringHandlerInterceptor;
import org.finalframework.auto.spring.factory.annotation.SpringWebMvcConfigurer;
import org.finalframework.core.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:23
 * @since 1.0
 */
@SpringWebMvcConfigurer
public class HandlerInterceptorWebMvcConfigurer implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorWebMvcConfigurer.class);

    private final List<HandlerInterceptor> handlerInterceptors;

    public HandlerInterceptorWebMvcConfigurer(
            ObjectProvider<List<HandlerInterceptor>> handlerInterceptorsObjectProvider) {
        this.handlerInterceptors = handlerInterceptorsObjectProvider.getIfAvailable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        handlerInterceptors.stream()
                // 仅支持通过注解注入的拦截器
                .filter(it -> AnnotatedElementUtils.hasAnnotation(it.getClass(), Component.class))
                .forEach(item -> {
                    SpringHandlerInterceptor annotation = item.getClass().getAnnotation(SpringHandlerInterceptor.class);
                    InterceptorRegistration interceptorRegistration = registry.addInterceptor(item);
                    if (item instanceof IHandlerInterceptor) {
                        IHandlerInterceptor handlerInterceptor = (IHandlerInterceptor) item;
                        if (Asserts.nonEmpty(handlerInterceptor.getPathPatterns())) {
                            interceptorRegistration.addPathPatterns(handlerInterceptor.getPathPatterns());
                        }
                        if (Asserts.nonEmpty(handlerInterceptor.getExcludePathPatterns())) {
                            interceptorRegistration.excludePathPatterns(handlerInterceptor.getExcludePathPatterns());
                        }
                        interceptorRegistration.order(handlerInterceptor.getOrder());


                    } else if (annotation != null) {
                        if (annotation.includes().length > 0) {
                            interceptorRegistration.addPathPatterns(annotation.includes());
                        }
                        if (annotation.excludes().length > 0) {
                            interceptorRegistration.excludePathPatterns(annotation.excludes());
                        }
                    }

                    Order order = item.getClass().getAnnotation(Order.class);
                    if (order != null) {
                        interceptorRegistration.order(order.value());
                    } else {
                        interceptorRegistration.order(Ordered.LOWEST_PRECEDENCE);
                    }

                    logger.info("==> add interceptor={}", item.getClass());
                });


    }

}
