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

package org.ifinalframework.web.converter;

import org.ifinalframework.util.format.DateFormatter;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DateConverterTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class String2DateConverterTest {

    private final String2DateConverter converter = new String2DateConverter();

    @Test
    void convert() {
        assertNotNull(converter.convert(DateFormatter.YYYYMMDD_HH_MM_SS.format(new Date())));
    }

}
