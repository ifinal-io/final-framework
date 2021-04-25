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

package org.ifinal.finalframework.web.interceptor;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.core.IUser;
import org.ifinal.finalframework.util.Reflections;
import org.ifinal.finalframework.web.annotation.auth.Auth;
import org.ifinal.finalframework.web.auth.AuthService;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnBean(AuthService.class)
@SuppressWarnings("rawtypes")
public class AuthHandlerInterceptor implements AsyncHandlerInterceptor {

    private final Map<Class<? extends Annotation>, AuthService> authServices = new HashMap<>();

    public AuthHandlerInterceptor(final ObjectProvider<AuthService<?, ?>> authServiceProvider) {

        for (AuthService<?, ?> authService : authServiceProvider) {
            Class<?> targetClass = AopUtils.getTargetClass(authService);
            Class authAnnotation = Reflections
                .findParameterizedInterfaceArgumentClass(targetClass, AuthService.class, 0);
            logger.info("register auth service of {} for @{}", targetClass, authAnnotation);
            authServices.put(authAnnotation, authService);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response,
        final @NonNull Object handler)
        throws Exception {

        Auth auth = findHandlerAuth(handler, Auth.class);

        Annotation authAnnotation = auth;

        if (auth != null) {
            IUser user = UserContextHolder.getUser();
            Class<? extends Annotation> annotation = auth.value();
            if (Auth.class.equals(annotation)) {
                authAnnotation = findHandlerAuth(handler, annotation);
            }
            Objects.requireNonNull(authAnnotation);

            authServices.get(annotation).auth(user, authAnnotation, request, response, handler);
        }

        return true;
    }

    private <A extends Annotation> A findHandlerAuth(final @NonNull Object handler, final Class<A> ann) {

        if (handler instanceof HandlerMethod) {
            A annotation = AnnotatedElementUtils.findMergedAnnotation(((HandlerMethod) handler).getMethod(), ann);
            if (annotation != null) {
                return annotation;
            }
            return AnnotatedElementUtils
                .findMergedAnnotation(((HandlerMethod) handler).getMethod().getDeclaringClass(), ann);
        }

        return null;
    }

}
