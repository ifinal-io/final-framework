/*
 * Copyright 2020-2024 the original author or authors.
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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RequestRewriteFilter
 *
 * @author mik
 * @since 1.5.6
 **/
@Slf4j
@RequiredArgsConstructor
public class RequestURIRewriteRequestFilter extends OncePerRequestFilter {

    private final String prefix;
    private final String replacement;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(prefix)) {
            final String newRequestURI = requestURI.replace(prefix, replacement);
            logger.info("rewrite requestURI: {} -> {}", requestURI, newRequestURI);
            request = new HttpRequestURIRequestWrapper(request, newRequestURI);
        }

        filterChain.doFilter(request, response);

    }

    private static class HttpRequestURIRequestWrapper extends HttpServletRequestWrapper {

        private final String requestURI;

        public HttpRequestURIRequestWrapper(HttpServletRequest request, String requestURI) {
            super(request);
            this.requestURI = requestURI;
        }

        @Override
        public String getRequestURI() {
            return requestURI;
        }
    }


}
