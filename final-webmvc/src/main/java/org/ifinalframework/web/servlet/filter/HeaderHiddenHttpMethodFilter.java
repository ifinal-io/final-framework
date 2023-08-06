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

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import org.ifinalframework.util.Asserts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author ilikly
 * @version 1.0.0
 * @see org.springframework.web.filter.HiddenHttpMethodFilter
 * @since 1.0.0
 */
@Component
public class HeaderHiddenHttpMethodFilter extends OncePerRequestFilter {

    /**
     * Default method parameter: {@code _method}.
     */
    public static final String DEFAULT_METHOD_HEADER = "Hidden-Http-Method";

    private static final List<String> ALLOWED_METHODS =
            Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));

    private String methodHeader = DEFAULT_METHOD_HEADER;

    /**
     * Set the header name to look for HTTP methods.
     *
     * @param methodHeader methodHeader
     * @see #DEFAULT_METHOD_HEADER
     */
    public void setMethodHeader(final String methodHeader) {

        Asserts.isEmpty(methodHeader, "'methodHeader' must not be empty");
        this.methodHeader = methodHeader;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest requestToUse = request;

        if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
            String headerValue = request.getHeader(this.methodHeader);
            if (StringUtils.hasLength(headerValue)) {
                String method = headerValue.toUpperCase(Locale.ENGLISH);
                if (ALLOWED_METHODS.contains(method)) {
                    requestToUse = new HttpMethodRequestWrapper(request, method);
                }
            }
        }

        filterChain.doFilter(requestToUse, response);
    }

    /**
     * Simple {@link HttpServletRequest} wrapper that returns the supplied method for {@link
     * HttpServletRequest#getMethod()}.
     */
    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

        private final String method;

        HttpMethodRequestWrapper(final HttpServletRequest request, final String method) {

            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }

    }

}


