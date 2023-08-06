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
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.IEnum;
import org.ifinalframework.util.Asserts;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create {@link EnumConverter} with {@link IEnum enum class} from {@link EnumConverterFactory}.
 *
 * @author ilikly
 * @version 1.0.0
 * @see org.springframework.core.convert.support.ConversionServiceFactory#registerConverters(Set, ConverterRegistry)
 * @since 1.0.0
 */
@Component
@SuppressWarnings("rawtypes")
public class EnumConverterFactory implements ConverterFactory<String, IEnum> {

    @NonNull
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(final @NonNull Class<T> targetType) {

        return new EnumConverter<>(targetType);
    }

    /**
     * 枚举类型转换器，实现将 {@link String} 映射到 {@link Enum} 类型，该枚举类型需要实现 {@link IEnum}接口。
     *
     * @author ilikly
     * @version 1.0.0
     * @since 1.0.0
     */
    public static class EnumConverter<T extends IEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        private final Map<String, T> enumMap;

        public EnumConverter(final Class<T> enumType) {

            this.enumType = enumType;
            final T[] enums = this.enumType.getEnumConstants();
            enumMap = Arrays.stream(enums).collect(Collectors.toMap(e -> e.getCode().toString(), e -> e));

        }

        @Override
        public T convert(final @NonNull String source) {

            return Asserts.isBlank(source) ? null : enumMap.get(source.trim());
        }

        @Override
        public String toString() {
            return enumType.getSimpleName() + "Converter";
        }

    }

}
