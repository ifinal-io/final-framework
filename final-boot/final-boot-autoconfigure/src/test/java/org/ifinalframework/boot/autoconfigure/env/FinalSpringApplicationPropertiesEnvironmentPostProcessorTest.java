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
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.env.StandardEnvironment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FinalSpringApplicationPropertiesEnvironmentPostProcessorTest.
 *
 * @author iimik
 * @version 1.2.3
 * @since 1.2.3
 */
class FinalSpringApplicationPropertiesEnvironmentPostProcessorTest {
    private FinalSpringApplicationPropertiesEnvironmentPostProcessor environmentPostProcessor
            = new FinalSpringApplicationPropertiesEnvironmentPostProcessor(
                    new DeferredLogs()
                            .getLog(FinalSpringApplicationPropertiesEnvironmentPostProcessor.class)
    );


    @Test
    void postProcessEnvironment() {
        final StandardEnvironment environment = new StandardEnvironment();
        environmentPostProcessor.postProcessEnvironment(environment, new SpringApplication());
        assertEquals(8080, environment.getProperty("server.port", int.class));
    }
}
