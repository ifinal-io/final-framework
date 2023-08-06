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

package org.ifinalframework.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Consumer;


/**
 * ConsumerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class ConsumerTest {

    @Test
    void andThen() {
        Consumer<StringBuilder> consumer = ((Consumer<StringBuilder>) sb -> sb.append("a"))
                .andThen(it -> it.append("b"))
                .andThen(it -> it.append("c"));
        StringBuilder stringBuilder = new StringBuilder();
        Optional.of(stringBuilder)
                .ifPresent(consumer);

        Assertions.assertEquals("abc", stringBuilder.toString());
    }

}
