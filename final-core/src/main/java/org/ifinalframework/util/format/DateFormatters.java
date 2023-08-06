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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateFormatters implements Formatters<Date> {

    public static final DateFormatters DEFAULT = new DateFormatters();

    private final List<DateFormatter> formatters = new ArrayList<>();

    public DateFormatters(final List<DateFormatter> formatters) {

        this.formatters.addAll(formatters);
    }

    public DateFormatters() {
        this(Arrays.asList(
                DateFormatter.YYYY_MM_DD_HH_MM_SS, DateFormatter.YYYY_MM_DD,
                DateFormatter.YYYY__MM__DD_HH_MM_SS, DateFormatter.YYYY__MM__DD,
                DateFormatter.YYYYMMDD_HH_MM_SS, DateFormatter.YYYYMMDD,
                DateFormatter.YYYYMMDDHHMMSS
        ));
    }

    @Override
    public Date parse(final String source) {

        if (Asserts.isEmpty(source)) {
            return null;
        }
        for (DateFormatter formatter : formatters) {
            if (formatter.matches(source)) {
                return formatter.parse(source);
            }
        }

        try {
            final long timestamp = Long.parseLong(source);
            return new Date(timestamp);
        } catch (Exception e) {
            return null;
        }

    }

}
