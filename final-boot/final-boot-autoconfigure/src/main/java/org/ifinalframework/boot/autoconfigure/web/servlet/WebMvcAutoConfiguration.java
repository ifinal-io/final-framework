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

package org.ifinalframework.boot.autoconfigure.web.servlet;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.ifinalframework.web.servlet.filter.RequestURIRewriteRequestFilter;

/**
 * WebServletAutoConfiguration
 *
 * @author iimik
 * @since 1.5.6
 **/
@Configuration
@EnableConfigurationProperties(WebRequestURIRewriteProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "spring.web.request-uri-rewrite", name = "prefix")
    public RequestURIRewriteRequestFilter requestURIRewriteRequestFilter(WebRequestURIRewriteProperties properties) {
        RequestURIRewriteRequestFilter filter = new RequestURIRewriteRequestFilter(properties.getPrefix(),
            properties.getReplacement());
        return filter;
    }

}
