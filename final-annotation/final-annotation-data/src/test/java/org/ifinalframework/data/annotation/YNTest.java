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

package org.ifinalframework.data.annotation;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.ifinalframework.core.IEnum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * YNTest.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
class YNTest {

    @Test
    void values() {
        assertEquals(3, YN.values().length);
        assertEquals(3, Arrays.stream(YN.values()).map(IEnum::getDesc).collect(Collectors.toSet()).size());
    }

    @Test
    void test() {
        assertEquals(-1, YN.DELETED.getCode());
        assertEquals(0, YN.NO.getCode());
        assertEquals(1, YN.YES.getCode());
    }

}