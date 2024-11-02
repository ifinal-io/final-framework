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

package org.ifinalframework.data.mybatis.util;

import org.ifinalframework.data.mybatis.entity.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MapperUtilsTest.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
class MapperUtilsTest {

    @Test
    void packageName() {
        assertEquals(User.class.getPackage().getName().replace(".entity", ".dao.mapper"), MapperUtils.packageName(User.class));
    }

    @Test
    void mapperName() {
        assertEquals(User.class.getSimpleName() + "Mapper", MapperUtils.mapperName(User.class));
    }

    @Test
    void mapperClassName() {
        assertEquals(
                String.join(".",
                        User.class.getPackage().getName().replace(".entity", ".dao.mapper"),
                        User.class.getSimpleName() + "Mapper"),
                MapperUtils.mapperClassName(User.class)
        );
    }
}