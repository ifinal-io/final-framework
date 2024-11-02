/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.web.servlet.method.security;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import org.ifinalframework.context.exception.ForbiddenException;
import org.ifinalframework.context.exception.UnauthorizedException;
import org.ifinalframework.data.security.DomainResourceAuth;
import org.ifinalframework.web.annotation.servlet.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * DomainResourceAuthHandlerInterceptor
 *
 * @author iimik
 * @since 1.5.2
 **/
@Slf4j
@Interceptor
public class SecurityDomainResourceAuthHandlerInterceptor implements HandlerInterceptor {
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {
            final DomainResourceAuth domainResourceAuth = handlerMethod.getMethodAnnotation(DomainResourceAuth.class);

            if (Objects.isNull(domainResourceAuth)) {
                return true;
            }

            final String resource = resolveName("resource", request);

            final String authority = String.join(":", domainResourceAuth.prefix(), resource,
                    domainResourceAuth.action().getAuthority());

            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


            final WebSecurityExpressionRoot root = new WebSecurityExpressionRoot(() -> authentication, request);
            root.setTrustResolver(authenticationTrustResolver);

            if (root.hasRole("ROOT")) {
                return true;
            }

            final boolean hasAuthority = root.hasAuthority(authority);

            if (!hasAuthority) {

                if (root.isAnonymous()) {
                    throw new UnauthorizedException("未登录");
                }

                logger.warn("您没有权限访问：{} {} {}", request.getMethod(), request.getRequestURI(), authority);

                throw new ForbiddenException("您没有权限：" + request.getMethod() + " " + request.getRequestURI());
            }

        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    protected String resolveName(String name, HttpServletRequest request) throws Exception {
        Map<String, String> uriTemplateVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return (uriTemplateVars != null ? uriTemplateVars.get(name) : null);
    }
}
