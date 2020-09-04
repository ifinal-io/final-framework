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

import lombok.Getter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 22:05:09
 * @since 1.0
 */
@SuppressWarnings("all")
public enum DateFormatterPattern {
    YYYY_MM_DD_HH_MM_SS("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss"),
    YYYY_MM_DD("^\\d{4}-\\d{2}-\\d{2}$", "yyyy-MM-dd"),
    YYYYMMDD_HH_MM_SS("^\\d{8}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyyMMdd HH:mm:ss"),
    YYYYMMDD("^\\d{8}$", "yyyyMMdd"),
    YYYY__MM__DD_HH_MM_SS("^\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss"),
    YYYY__MM__DD("^\\d{4}/\\d{2}/\\d{2}$", "yyyy/MM/dd"),
    YYYYMMDDHHMMSS("^\\d{14}$", "yyyyMMddHHmmss"),
    ;
    @Getter
    private final String regex;
    @Getter
    private final String pattern;

    DateFormatterPattern(String regex, String pattern) {
        this.regex = regex;
        this.pattern = pattern;
    }
}
