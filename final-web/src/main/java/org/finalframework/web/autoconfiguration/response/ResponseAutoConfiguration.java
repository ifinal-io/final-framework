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

package org.finalframework.web.autoconfiguration.response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import org.ifinal.auto.spring.factory.annotation.SpringAutoConfiguration;

import java.util.Optional;

import org.finalframework.web.response.advice.ResponsibleResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0.0
 * @see ResponsibleResponseBodyAdvice
 * @since 1.0.0
 */
@Configuration
@SpringAutoConfiguration
@ConditionalOnClass(ResponsibleResponseBodyAdvice.class)
@EnableConfigurationProperties(ResponseProperties.class)
public class ResponseAutoConfiguration implements ApplicationContextAware {

    private final ResponseProperties properties;

    public ResponseAutoConfiguration(final ResponseProperties properties) {

        this.properties = properties;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {

        Optional.of(applicationContext.getBean(ResponsibleResponseBodyAdvice.class))
            .ifPresent(it -> it.setSyncStatus(properties.isSyncStatus()));
    }

}
