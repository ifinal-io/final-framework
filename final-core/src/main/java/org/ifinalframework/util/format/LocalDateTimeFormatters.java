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

package org.ifinalframework.util.format;

import org.ifinalframework.util.Asserts;
import org.ifinalframework.util.Dates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LocalDateTimeFormatters implements Formatters<LocalDateTime> {

    public static final LocalDateTimeFormatters DEFAULT = new LocalDateTimeFormatters();

    private final List<LocalDateTimeFormatter> dateFormatters = new ArrayList<>();

    public LocalDateTimeFormatters(final List<LocalDateTimeFormatter> dateFormatters) {

        this.dateFormatters.addAll(dateFormatters);
    }

    public LocalDateTimeFormatters() {
        this(Arrays.asList(
            LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS,
            LocalDateTimeFormatter.YYYY2_MM2_DD_HH_MM_SS,
            LocalDateTimeFormatter.YYYYMMDD_HH_MM_SS,
            LocalDateTimeFormatter.YYYYMMDDHHMMSS
        ));
    }

    @Override
    public LocalDateTime parse(final String source) {

        if (Asserts.isEmpty(source)) {
            return null;
        }
        for (LocalDateTimeFormatter formatter : dateFormatters) {
            if (formatter.matches(source)) {
                return formatter.parse(source);
            }
        }

        try {
            final long timestamp = Long.parseLong(source);
            return Dates.from(new Date(timestamp));
        } catch (Exception e) {
            return null;
        }

    }

}
