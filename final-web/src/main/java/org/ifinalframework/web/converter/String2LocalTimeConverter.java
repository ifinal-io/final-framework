/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * String2LocalTimeConverter.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
public class String2LocalTimeConverter implements Converter<String, LocalTime> {
    @Nullable
    @Override
    public LocalTime convert(String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }

        return LocalTime.parse(source, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
