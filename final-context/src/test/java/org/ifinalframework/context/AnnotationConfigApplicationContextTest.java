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

package org.ifinalframework.context;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * AnnotationConfigApplicationContextTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@ComponentScan
public class AnnotationConfigApplicationContextTest {

    @Test
    void context() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationConfigApplicationContextTest.class);
        for (final String name : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(name);
            logger.info("{}={}", name, beanDefinition.getBeanClassName());
        }
        context.refresh();
        for (final String name : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(name);
            logger.info("{}={}", name, beanDefinition.getBeanClassName());
        }
        AnnotationConfigApplicationContextTest bean = context.getBean(AnnotationConfigApplicationContextTest.class);

        Assertions.assertEquals(AnnotationConfigApplicationContextTest.class, bean.getClass());

        Assertions.assertNotNull(context.getBean(HelloBean.class));
    }

}
