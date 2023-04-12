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

package org.ifinalframework.context.initializer;

import java.util.ServiceLoader;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import org.ifinalframework.auto.spring.factory.annotation.SpringFactory;
import org.ifinalframework.javassist.JavaAssistProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * JavaAssistApplicationStartingListener.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
@SpringFactory(ApplicationListener.class)
public class JavaAssistApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        for (final JavaAssistProcessor javaAssistProcessor : ServiceLoader.load(JavaAssistProcessor.class)) {
            logger.info("start {}", javaAssistProcessor);
        }
    }
}
