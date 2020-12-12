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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    public static final LocalDateTimeFormatter YYYY_MM_DD_HH_MM_SS = new LocalDateTimeFormatter(DateFormatterPattern.YYYY_MM_DD_HH_MM_SS);

    public static final LocalDateTimeFormatter YYYY2_MM2_DD_HH_MM_SS = new LocalDateTimeFormatter(DateFormatterPattern.YYYY__MM__DD_HH_MM_SS);

    public static final LocalDateTimeFormatter YYYYMMDDHHMMSS = new LocalDateTimeFormatter(DateFormatterPattern.YYYYMMDDHHMMSS);

    public static final LocalDateTimeFormatter YYYYMMDD_HH_MM_SS = new LocalDateTimeFormatter(DateFormatterPattern.YYYYMMDD_HH_MM_SS);

    private final String regex;

    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateTimeFormatter(final String regex, final String pattern) {

        this.regex = regex;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public LocalDateTimeFormatter(final DateFormatterPattern pattern) {

        this(pattern.getRegex(), pattern.getPattern());
    }

    @Override
    public LocalDateTime parse(final String source) {

        return LocalDateTime.parse(source, dateTimeFormatter);
    }

    @Override
    public String format(final LocalDateTime target) {

        return target.format(dateTimeFormatter);
    }

    @Override
    public boolean matches(final String source) {

        return source.matches(regex);
    }

}
