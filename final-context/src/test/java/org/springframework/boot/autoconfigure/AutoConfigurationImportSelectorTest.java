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

package org.springframework.boot.autoconfigure;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

import org.ifinalframework.ContextApplicationContext;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * AutoConfigurationImportSelectorTest.
 *
 * @author iimik
 * @version 1.0.0
 * @see org.springframework.context.annotation.ImportSelector
 * @since 1.0.0
 */
@Slf4j
class AutoConfigurationImportSelectorTest {

    @Test
    void selectImports() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        AutoConfigurationImportSelector selector = new AutoConfigurationImportSelector();
        selector.setEnvironment(context.getEnvironment());
        selector.setResourceLoader(context);
        selector.setBeanFactory(context.getBeanFactory());
        String[] imports = selector.selectImports(AnnotationMetadata.introspect(ContextApplicationContext.class));
        for (String item : imports) {
            logger.info(item);
        }
    }

}
