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

package org.ifinalframework.feign.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * HeadersRequestInterceptor
 *
 * @author iimik
 * @since 1.5.6
 **/
@Component
public class HeadersRequestInterceptor implements RequestInterceptor {

    private static final List<String> DEFAULT_HEADERS = List.of(HttpHeaders.COOKIE, HttpHeaders.AUTHORIZATION);

    private final List<String> headers;

    public HeadersRequestInterceptor(List<String> headers) {
        this.headers = headers;
    }

    public HeadersRequestInterceptor() {
        this(DEFAULT_HEADERS);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {

            headers.forEach(header -> {
                final String value = servletRequestAttributes.getRequest().getHeader(header);
                if (value != null) {
                    requestTemplate.header(header, value);
                }
            });

        }
    }
}
