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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link Date}格式化器，可使用 {@link #format(Date)} 格式化{@link Date}或 使用{@link #parse(String)}奖{@link String}解析成{@link Date}
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-15 21:48:24
 * @since 1.0
 */
@SuppressWarnings("all")
public class DateFormatter implements Formatter<Date> {

    public static final DateFormatter YYYY_MM_DD_HH_MM_SS = new DateFormatter(DateFormatterPattern.YYYY_MM_DD_HH_MM_SS);
    public static final DateFormatter YYYY_MM_DD = new DateFormatter(DateFormatterPattern.YYYY_MM_DD);
    public static final DateFormatter YYYY__MM__DD_HH_MM_SS = new DateFormatter(DateFormatterPattern.YYYY__MM__DD_HH_MM_SS);
    public static final DateFormatter YYYY__MM__DD = new DateFormatter(DateFormatterPattern.YYYY__MM__DD);
    public static final DateFormatter YYYYMMDD_HH_MM_SS = new DateFormatter(DateFormatterPattern.YYYYMMDD_HH_MM_SS);
    public static final DateFormatter YYYYMMDD = new DateFormatter(DateFormatterPattern.YYYYMMDD);
    public static final DateFormatter YYYYMMDDHHMMSS = new DateFormatter(DateFormatterPattern.YYYYMMDDHHMMSS);

    private final String regex;
    private final String pattern;

    public DateFormatter(String regex, String pattern) {
        this.regex = regex;
        this.pattern = pattern;
    }

    public DateFormatter(DateFormatterPattern pattern) {
        this(pattern.getRegex(), pattern.getPattern());
    }

    @Override
    public Date parse(String source) {
        try {
            return Assert.isEmpty(source) ? null : new SimpleDateFormat(pattern).parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String format(Date target) {
        return new SimpleDateFormat(pattern).format(target);
    }

    @Override
    public boolean matches(String source) {
        return source.matches(regex);
    }
}
