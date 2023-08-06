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

package org.ifinalframework.web.servlet.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import org.ifinalframework.context.FinalContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * FinalContextFilter.
 *
 * @author ilikly
 * @version 1.4.3
 * @since 1.4.3
 */
@Slf4j
public class FinalContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            for (Cookie cookie : request.getCookies()) {
                for (FinalContext finalContext : FinalContext.values()) {
                    if (finalContext.name().equalsIgnoreCase(cookie.getName())) {
                        String cookieValue = cookie.getValue();
                        finalContext.set(cookieValue);
                        logger.info("setFinalContext:{}={}", finalContext.name(), cookieValue);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } finally {
            FinalContext.reset();
        }
    }
}
