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

package org.ifinalframework.context.beans.factory.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

/**
 * BeanDefinitionReadersTest.
 *
 * @author likly
 * @version 1.2.4
 * @since 1.2.4
 */
@ExtendWith(MockitoExtension.class)
class BeanDefinitionReadersTest {

    @Mock
    private BeanDefinitionRegistry registry;

    @Mock
    private Environment environment;

    @Mock
    private ResourceLoader resourceLoader;

    @Test
    void reader() {
        Assertions.assertEquals(XmlBeanDefinitionReader.class, BeanDefinitionReaders.reader("*.xml"));
    }

    @Test
    void instance() {
        final BeanDefinitionReader reader = BeanDefinitionReaders.instance("*.xml", registry, resourceLoader, environment);
        Assertions.assertNotNull(reader);
    }
}
