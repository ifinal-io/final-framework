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

package org.ifinalframework.security.web.authentication;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.ifinalframework.security.core.TokenAuthenticationService;

/**
 * TokenAuthenticationFilter.
 *
 * @author ilikly
 * @version 1.3.3
 * @since 1.3.3
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";

    @Resource
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(HEADER);

        if (ObjectUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (!ObjectUtils.isEmpty(cookies)) {
                token = Arrays.stream(cookies)
                        .filter(it -> "token".equals(it.getName()))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null);
            }
        }

        if (!ObjectUtils.isEmpty(token)) {
            logger.info("token-->" + token);

            Authentication authentication = tokenAuthenticationService.auth(token);

            if (authentication instanceof AbstractAuthenticationToken) {
                ((AbstractAuthenticationToken) authentication).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }

            // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }
}


