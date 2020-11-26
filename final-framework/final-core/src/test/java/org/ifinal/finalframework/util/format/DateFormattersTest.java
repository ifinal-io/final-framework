/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.ifinal.finalframework.util.format;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateFormattersTest {

    private DateFormatters dateFormatters = DateFormatters.DEFAULT;

    @Test
    @SuppressWarnings("all")
    public void parse() {
        String YYYY_MM_DD_HH_MM_SS = "2019-02-14 12:13:14";
        Assertions.assertEquals(YYYY_MM_DD_HH_MM_SS, DateFormatter.YYYY_MM_DD_HH_MM_SS.format(dateFormatters.parse(YYYY_MM_DD_HH_MM_SS)));
        String YYYY_MM_DD = "2019-02-14";
        Assertions.assertEquals(YYYY_MM_DD, DateFormatter.YYYY_MM_DD.format(dateFormatters.parse(YYYY_MM_DD)));

        String YYYY__MM__DD_HH_MM_SS = "2019/02/14 12:13:14";
        Assertions.assertEquals(YYYY__MM__DD_HH_MM_SS, DateFormatter.YYYY__MM__DD_HH_MM_SS.format(dateFormatters.parse(YYYY__MM__DD_HH_MM_SS)));
        String YYYY__MM__DD = "2019/02/14";
        Assertions.assertEquals(YYYY__MM__DD, DateFormatter.YYYY__MM__DD.format(dateFormatters.parse(YYYY__MM__DD)));

        String YYYYMMDD_HH_MM_SS = "20190214 12:13:14";
        Assertions.assertEquals(YYYYMMDD_HH_MM_SS, DateFormatter.YYYYMMDD_HH_MM_SS.format(dateFormatters.parse(YYYYMMDD_HH_MM_SS)));
        String YYYYMMDD = "20190214";
        Assertions.assertEquals(YYYYMMDD, DateFormatter.YYYYMMDD.format(dateFormatters.parse(YYYYMMDD)));

        String YYYYMMDDHHMMSS = "20190214121314";
        Assertions.assertEquals(YYYYMMDDHHMMSS, DateFormatter.YYYYMMDDHHMMSS.format(dateFormatters.parse(YYYYMMDDHHMMSS)));
    }
}