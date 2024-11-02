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

package org.ifinalframework.data.env;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import org.ifinalframework.data.annotation.AbsEntity;
import org.ifinalframework.data.annotation.Column;
import org.ifinalframework.data.mapping.Entity;
import org.ifinalframework.data.mapping.EntityCache;
import org.ifinalframework.data.mapping.Property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import lombok.Data;

/**
 * EntityFactoryEnvironmentPostProcessorTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class EntityFactoryEnvironmentPostProcessorTest {

    @Data
    private static class MyEntity extends AbsEntity {
        @Column("${spring.column}")
        private String column;
    }

    @ParameterizedTest
    @ValueSource(strings = {"vendor", "_tenant"})
    void postProcessEnvironment(String value) {
        final EntityFactoryEnvironmentPostProcessor entityFactoryEnvironmentPostProcessor = new EntityFactoryEnvironmentPostProcessor();
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        standardEnvironment.getPropertySources().addLast(new MapPropertySource("map", Collections.singletonMap("spring.column", value)));
        entityFactoryEnvironmentPostProcessor.postProcessEnvironment(standardEnvironment, null);

        final Entity<MyEntity> properties = EntityCache.getInstance().get(MyEntity.class);
        final Property column = properties.getRequiredPersistentProperty("column");
        Assertions.assertEquals(value, column.getColumn());

    }

}