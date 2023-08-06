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

package org.springframework.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.junit.jupiter.api.Assertions;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringApplicationTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
class SpringApplicationTest {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringApplicationTest.class);
        Class<?> mainApplicationClass = application.getMainApplicationClass();
        logger.info("mainApplicationClass={}", mainApplicationClass);
        Assertions.assertEquals(SpringApplicationTest.class, mainApplicationClass);
        application.run(args);
    }

}
