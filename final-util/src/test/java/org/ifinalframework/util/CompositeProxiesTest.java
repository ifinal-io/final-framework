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

package org.ifinalframework.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * CompositeProxiesTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
class CompositeProxiesTest {

    @FunctionalInterface
    interface Callback {
        void say(String haha);
    }


    @Test
    void composite() {
        final Callback composite = CompositeProxies.composite(Callback.class, Arrays.asList(
                (key) -> logger.info("from one {}", key),
                (key) -> logger.info("from two {}", key)
        ));

        composite.say("haha!");
    }

}