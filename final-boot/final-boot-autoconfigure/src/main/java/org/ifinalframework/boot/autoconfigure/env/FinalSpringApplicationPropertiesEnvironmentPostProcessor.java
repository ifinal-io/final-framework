/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.boot.autoconfigure.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import org.ifinalframework.auto.spring.factory.annotation.SpringFactory;

import org.apache.commons.logging.Log;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

/**
 * Load {@code META-INF/final-spring-application.properties} into {@link ConfigurableEnvironment}.
 *
 * @author iimik
 * @version 1.2.3
 * @since 1.2.3
 */

@SpringFactory(EnvironmentPostProcessor.class)
@RequiredArgsConstructor
public class FinalSpringApplicationPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public static final String FINAL_SPRING_APPLICATION_PROPERTIES = "META-INF/final-spring-application.properties";
    private final Log logger;


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {


        try {
            final ResourcePropertySource propertySource = new ResourcePropertySource("finalSpringApplication",
                    FINAL_SPRING_APPLICATION_PROPERTIES, application.getClassLoader());

            environment.getPropertySources().addLast(propertySource);
        } catch (IOException e) {
            logger.error("load " + FINAL_SPRING_APPLICATION_PROPERTIES + " error:", e);
            throw new IllegalArgumentException(e);
        }


    }
}
