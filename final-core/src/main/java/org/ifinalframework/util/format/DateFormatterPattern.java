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

import lombok.Getter;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public enum DateFormatterPattern {
    /**
     * yyyy-MM-dd HH:mm:ss.
     */
    YYYY_MM_DD_HH_MM_SS("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss"),
    /**
     * yyyy-MM-dd.
     */
    YYYY_MM_DD("^\\d{4}-\\d{2}-\\d{2}$", "yyyy-MM-dd"),
    /**
     * yyyyMMdd HH:mm:ss.
     */
    YYYYMMDD_HH_MM_SS("^\\d{8}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyyMMdd HH:mm:ss"),
    /**
     * yyyyMMdd.
     */
    YYYYMMDD("^\\d{8}$", "yyyyMMdd"),
    /**
     * yyyy/MM/dd HH:mm:ss.
     */
    YYYY__MM__DD_HH_MM_SS("^\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss"),
    /**
     * yyyy/MM/dd.
     */
    YYYY__MM__DD("^\\d{4}/\\d{2}/\\d{2}$", "yyyy/MM/dd"),
    /**
     * yyyyMMddHHmmss.
     */
    YYYYMMDDHHMMSS("^\\d{14}$", "yyyyMMddHHmmss");

    @Getter
    private final String regex;

    @Getter
    private final String pattern;

    DateFormatterPattern(final String regex, final String pattern) {

        this.regex = regex;
        this.pattern = pattern;
    }
}
