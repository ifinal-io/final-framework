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

package org.ifinalframework.web.servlet.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Auto detect {@link HandlerMethodArgumentResolver} from spring context.
 *
 * @author ilikly
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
@Component
public class HandlerMethodArgumentResolverWebMvcConfigurer implements WebMvcConfigurer {
    private final List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers;

    public HandlerMethodArgumentResolverWebMvcConfigurer(ObjectProvider<HandlerMethodArgumentResolver> provider) {
        this.handlerMethodArgumentResolvers = provider.orderedStream().collect(Collectors.toList());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        if (logger.isInfoEnabled()) {
            for (HandlerMethodArgumentResolver resolver : handlerMethodArgumentResolvers) {
                logger.info("found HandlerMethodArgumentResolver: {}", resolver.getClass());
            }
        }
        resolvers.addAll(handlerMethodArgumentResolvers);
    }
}


