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

package org.ifinalframework.security.web.servlet.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import org.ifinalframework.context.exception.UnauthorizedException;

/**
 * PrePostAnnotationHandlerInterceptor.
 *
 * @author ilikly
 * @version 1.4.0
 * @since 1.4.0
 */
@Component
public class PrePostAnnotationHandlerInterceptor implements HandlerInterceptor {

    private final Predicate<HandlerMethod> predicate = new PrePostAnnotationPredicate();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {

            if (predicate.test((HandlerMethod) handler)) {
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    throw new UnauthorizedException("用户未登录");
                }
            }

        }

        return true;
    }

    private static class PrePostAnnotationPredicate implements Predicate<HandlerMethod> {

        @Override
        public boolean test(HandlerMethod handlerMethod) {
            if (handlerMethod.hasMethodAnnotation(PreAuthorize.class)
                    || handlerMethod.hasMethodAnnotation(PostAuthorize.class)) {
                return true;
            }

            Class<?> beanType = handlerMethod.getBeanType();

            return beanType.isAnnotationPresent(PreAuthorize.class) || beanType.isAnnotationPresent(PostAuthorize.class);

        }
    }
}


