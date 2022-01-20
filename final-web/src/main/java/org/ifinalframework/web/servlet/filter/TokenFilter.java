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

package org.ifinalframework.web.servlet.filter;

import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.IUser;
import org.ifinalframework.web.auth.TokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@ConditionalOnBean(TokenService.class)
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    @SuppressWarnings("rawtypes")
    private TokenService<? extends IUser> tokenService;

    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest request,
        final @NonNull HttpServletResponse response,
        final @NonNull FilterChain filterChain) throws ServletException, IOException {

        UserContextHolder.setUser(tokenService.token(request, response), true);
        filterChain.doFilter(request, response);

    }

}
