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

package org.finalframework.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoggerTest {

    private static final Logger outter = LoggerFactory.getLogger("outter");

    private static final Logger inner = LoggerFactory.getLogger("outter.inner");

    @Test
    void logger() {
        outter.info("outter");
        inner.info("inner");
    }

}
