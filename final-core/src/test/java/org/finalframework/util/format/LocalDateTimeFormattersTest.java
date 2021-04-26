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

package org.finalframework.util.format;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class LocalDateTimeFormattersTest {

    private LocalDateTimeFormatters dateFormatters = LocalDateTimeFormatters.DEFAULT;

    @Test()
    void parse() {

        String datetime = "2019-02-14 12:13:14";
        Assertions
            .assertEquals(datetime, LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS.format(dateFormatters.parse(datetime)));

        datetime = "2019/02/14 12:13:14";
        Assertions.assertEquals(datetime,
            LocalDateTimeFormatter.YYYY2_MM2_DD_HH_MM_SS.format(dateFormatters.parse(datetime)));

        datetime = "20190214 12:13:14";
        Assertions
            .assertEquals(datetime, LocalDateTimeFormatter.YYYYMMDD_HH_MM_SS.format(dateFormatters.parse(datetime)));

        datetime = "20190214121314";
        Assertions.assertEquals(datetime, LocalDateTimeFormatter.YYYYMMDDHHMMSS.format(dateFormatters.parse(datetime)));
    }

}
