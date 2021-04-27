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
 */

package org.ifinalframework.web.autoconfiguration.trace;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import org.ifinalframework.auto.spring.factory.annotation.SpringAutoConfiguration;
import org.ifinalframework.web.interceptor.TraceHandlerInterceptor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@SpringAutoConfiguration
@ConditionalOnBean(TraceHandlerInterceptor.class)
@EnableConfigurationProperties(TraceProperties.class)
public class TraceHandlerInterceptorAutoConfiguration implements InitializingBean {

    private final TraceProperties properties;

    private final TraceHandlerInterceptor traceHandlerInterceptor;

    public TraceHandlerInterceptorAutoConfiguration(final TraceProperties properties,
        final TraceHandlerInterceptor traceHandlerInterceptor) {

        this.properties = properties;
        this.traceHandlerInterceptor = traceHandlerInterceptor;
    }

    @Override
    public void afterPropertiesSet() {

        traceHandlerInterceptor.setTraceName(properties.getName());
        traceHandlerInterceptor.setTraceName(properties.getParam());
        traceHandlerInterceptor.setHeaderName(properties.getHeader());
    }

}
