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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 22:35:18
 * @since 1.0
 */
public class LocalDateTimeFormatters implements Formatters<LocalDateTime> {
    public static final LocalDateTimeFormatters DEFAULT = new LocalDateTimeFormatters();
    private final List<LocalDateTimeFormatter> dateFormatters = new ArrayList<>();

    public LocalDateTimeFormatters(List<LocalDateTimeFormatter> dateFormatters) {
        this.dateFormatters.addAll(dateFormatters);
    }

    public LocalDateTimeFormatters() {
        this(Arrays.asList(
                LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS,
                LocalDateTimeFormatter.YYYY__MM__DD_HH_MM_SS,
                LocalDateTimeFormatter.YYYYMMDD_HH_MM_SS,
                LocalDateTimeFormatter.YYYYMMDDHHMMSS
        ));
    }

    @Override
    public LocalDateTime parse(String source) {
        if (Assert.isEmpty(source)) return null;
        for (LocalDateTimeFormatter formatter : dateFormatters) {
            if (formatter.matches(source)) {
                return formatter.parse(source);
            }
        }
        return null;
    }
}
