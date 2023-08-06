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

package org.ifinalframework.core.io.support;

import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringFactoriesLoaderTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j(topic = "springFactory")
class SpringFactoriesLoaderTest {

    @Test
    void loadApplicationListenerFactoryNames() {
        List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(ApplicationListener.class, null);
        factoryNames.forEach(it -> logger.info("{}", it));
    }

    @Test
    void loadApplicationListenerFactories() {
        List<ApplicationListener> listeners = SpringFactoriesLoader.loadFactories(ApplicationListener.class, null);
        listeners.forEach(it -> logger.info("{}", it.getClass()));
    }

}
