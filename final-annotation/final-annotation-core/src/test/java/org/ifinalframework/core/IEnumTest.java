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
 *
 */

package org.ifinalframework.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 * IEnumTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IEnumTest {

    @Getter
    @RequiredArgsConstructor
    private enum YN implements IEnum<Integer> {
        YES(1, "YES"),
        NO(0, "NO");

        private final Integer code;

        private final String desc;
    }

    @Test
    void valueOf() {
        Assertions.assertNull(IEnum.valueOf(YN.class, null));
        Assertions.assertNull(IEnum.valueOf(YN.class, 2));
        Assertions.assertEquals(YN.YES, IEnum.valueOf(YN.class, 1));
        Assertions.assertEquals(YN.NO, IEnum.valueOf(YN.class, 0));
    }

    @Test
    void should_throw_npe_when_type_is_null() {
        Assertions.assertThrows(NullPointerException.class, () -> IEnum.valueOf(null, "yes"));
    }

}
