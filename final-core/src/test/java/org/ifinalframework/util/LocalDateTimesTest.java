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

package org.ifinalframework.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ilikly
 * @version 1.3.1
 **/
@ExtendWith(MockitoExtension.class)
class LocalDateTimesTest {

    @Test
    void localDateTime() {
        System.out.println(LocalDateTime.now(ZoneOffset.UTC.normalized()));
        System.out.println(LocalDateTimes.localDateTime(LocalDateTime.now(), ZoneId.systemDefault(), ZoneOffset.UTC.normalized()));
    }

    @Test
    void test() {
        System.out.println(new Date(2022 - 1900, Calendar.FEBRUARY, 12, 8, 0, 0).getTime());
        System.out.println(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
    }
}