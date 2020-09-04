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

package org.finalframework.json.jackson;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 13:17:56
 * @since 1.0
 */
public class DateTimeFormatterContext {
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ThreadLocal<DateTimeFormatter> dateTimeFormatter = new ThreadLocal<>();
    private static final ThreadLocal<ZoneOffset> zoneOffset = new ThreadLocal<>();

    public static DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatterContext.dateTimeFormatter.get();
//        return dateTimeFormatter == null ? DEFAULT_DATE_FORMATTER : dateTimeFormatter;
        return dateTimeFormatter;
    }

    public static void setDateTimeFormatter(DateTimeFormatter formatter) {
        dateTimeFormatter.set(formatter);
    }

    public static ZoneOffset getZoneOffset() {
        return DateTimeFormatterContext.zoneOffset.get();
    }

    public static void setZoneOffset(ZoneOffset zoneOffset) {
        DateTimeFormatterContext.zoneOffset.set(zoneOffset);
    }

}
