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

package org.ifinalframework.context.resource;

import java.util.Collection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.ifinalframework.FinalFramework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BeanFactoryResourceValueManagerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class BeanFactoryResourceValueManagerTest {

    @Test
    void resourceValue() {
        System.setProperty("spring.xml.ignore", "false");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                FinalFramework.class);

        ResourceValueManager manager = context.getBean(ResourceValueManager.class);

        ResourceValueEntity entity = context.getBean(ResourceValueEntity.class);

        Collection<ResourceValueHolder> holders = manager.getResourceValueHolders("config.name");

        assertEquals(1, holders.size());

        manager.setValue("config.name", "123456");
        assertEquals("123456", entity.getName());

        manager.setValue("config.stores", "[2,1]");
        assertTrue(entity.getStores().contains(1));

    }

}
