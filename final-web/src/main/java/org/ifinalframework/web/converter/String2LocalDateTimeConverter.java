/*
 * Copyright 2020-2021 the original author or authors.
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
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import org.ifinalframework.util.format.LocalDateTimeFormatters;

import java.time.LocalDateTime;

/**
 * LocalDateTimeConverter.
 *
 * <p>Convert {@link String} to {@link LocalDateTime}.</p>
 *
 * Supports patterns:
 * <ul>
 *     <li>{@code yyyy-MM-dd HH:mm:ss}</li>
 *     <li>{@code yyyy/MM/dd HH:mm:ss}</li>
 *     <li>{@code yyyyMMdd HH:mm:ss}</li>
 *     <li>{@code yyyyMMddHHmmss}</li>
 * </ul>
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class String2LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime convert(@NonNull String source) {
        return LocalDateTimeFormatters.DEFAULT.parse(source);
    }

}
