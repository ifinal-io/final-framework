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

package org.ifinalframework.security.web.method;

import java.util.Set;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;

import org.ifinalframework.context.exception.ForbiddenException;

/**
 * SpelHandlerMethodPreAuthenticate.
 *
 * @author ilikly
 * @version 1.5.0
 * @see org.springframework.security.access.prepost.PreAuthorize
 * @since 1.5.0
 */
public abstract class AbstractSpelHandlerMethodPreAuthenticate implements HandlerMethodPreAuthenticate {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    public void authenticate(HandlerMethod handler, HttpMethod method, Set<String> requestPattern) {

        final Set<String> spelAuthroizes = getSpelAuthroizes(method, requestPattern);

        if (CollectionUtils.isEmpty(spelAuthroizes)) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {
        };

        for (final String spelAuthroize : spelAuthroizes) {
            final Boolean value = spelExpressionParser.parseExpression(spelAuthroize).getValue(securityExpressionRoot, boolean.class);
            if (!Boolean.TRUE.equals(value)) {
                throw new ForbiddenException("您没有权限：" + method + " " + String.join(",", requestPattern));
            }
        }


    }

    protected abstract Set<String> getSpelAuthroizes(HttpMethod method, Set<String> requestPatterns);
}
