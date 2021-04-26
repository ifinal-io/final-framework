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

package org.finalframework.web.converter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.annotation.Aspect;
import org.finalframework.context.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class String2ClassConverter implements Converter<String, Class<?>> {

    public static final Logger logger = LoggerFactory.getLogger(String2ClassConverter.class);

    private static final Map<String, Class<? extends Annotation>> annotations
        = Stream.of(
        RestController.class, Controller.class,
        Component.class, Service.class, Configuration.class,
        EnableAutoConfiguration.class, Aspect.class
    ).collect(Collectors.toMap(Class::getSimpleName, Function.identity()));

    @Override
    public Class<?> convert(final @NonNull String source) {

        try {
            return annotations.containsKey(source) ? annotations.get(source) : Class.forName(source);
        } catch (ClassNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

}
