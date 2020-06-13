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

package org.finalframework.core.formatter;

import org.finalframework.core.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 日期格式化器，将{@link String}解析成{@link Date}或将{@link Date}格式化成指定的格式{@link DateFormatterPattern#pattern}
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-15 21:58:15
 * @since 1.0
 */
public class DateFormatters implements Formatters<Date> {

    public static final DateFormatters DEFAULT = new DateFormatters();

    private final List<DateFormatter> dateFormatters = new ArrayList<>();

    public DateFormatters(List<DateFormatter> dateFormatters) {
        this.dateFormatters.addAll(dateFormatters);
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
    public Date parse(String source) {
        if (Assert.isEmpty(source)) {
            return null;
        }
        for (DateFormatter formatter : dateFormatters) {
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
