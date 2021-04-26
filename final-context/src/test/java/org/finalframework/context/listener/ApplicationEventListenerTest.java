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

package org.finalframework.context.listener;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.ContextApplicationContext;
import org.junit.jupiter.api.Test;

/**
 * ApplicationContextInitializedEventListenerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ApplicationEventListenerTest {

    @Test
    void onApplicationEvent() {
        assertDoesNotThrow(() -> {
            SpringApplication application = new SpringApplication(ContextApplicationContext.class);

            application.addListeners(
                new ApplicationContextInitializedEventListener(),
                new ApplicationEnvironmentPreparedEventListener(),
                new ApplicationFailedEventListener(),
                new ApplicationPreparedEventListener(),
                new ApplicationReadyEventListener(),
                new ApplicationStartedEventListener(),
                new ApplicationStartingEventListener()
            );


            ConfigurableApplicationContext context = application.run();
        });
    }

}
