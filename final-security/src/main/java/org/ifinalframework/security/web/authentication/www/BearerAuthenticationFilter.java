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

package org.ifinalframework.security.web.authentication.www;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.ifinalframework.security.core.TokenAuthenticationService;

import lombok.extern.slf4j.Slf4j;

/**
 * BearerAuthenticationFilter.
 *
 * @author ilikly
 * @version 1.4.0
 * @since 1.4.0
 * @see org.springframework.security.web.authentication.www.BasicAuthenticationFilter
 */
@Slf4j
@Component
public class BearerAuthenticationFilter extends OncePerRequestFilter {
    private final BearerAuthenticationConverter authenticationConverter;
    private final CookieAuthenticationConverter cookieAuthenticationConverter;

    public BearerAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.authenticationConverter = new BearerAuthenticationConverter(tokenAuthenticationService);
        this.cookieAuthenticationConverter = new CookieAuthenticationConverter(tokenAuthenticationService);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            chain.doFilter(request, response);
//        }
        Authentication authentication = authenticationConverter.convert(request);

        if(Objects.isNull(authentication)){
            authentication =  cookieAuthenticationConverter.convert(request);
        }

        if (Objects.isNull(authentication)) {
            chain.doFilter(request, response);
            return;
        }

        String username = authentication.getName();
        logger.trace("Found username {} in Bearer Authorization header", username);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }
}


