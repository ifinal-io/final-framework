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

package org.finalframework.spring.web.converter;


import org.finalframework.core.Assert;
import org.finalframework.data.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 枚举类型转换器，实现将 {@link String} 映射到 {@link Enum} 类型，该枚举类型需要实现 {@link IEnum}接口。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-11 15:58:57
 * @since 1.0
 */
public class EnumConverter<T extends IEnum> implements Converter<String, T> {
    private final Class<T> enumType;
    private final Map<String, T> enumMap;

    public EnumConverter(Class<T> enumType) {
        this.enumType = enumType;
        final T[] enums = this.enumType.getEnumConstants();
        enumMap = Arrays.stream(enums).collect(Collectors.toMap(e -> e.getCode().toString(), e -> e));

    }

    @Override
    public T convert(String source) {
        return Assert.isBlank(source) ? null : enumMap.get(source.trim());
    }

    @Override
    public String toString() {
        return enumType.getSimpleName() + "Converter";
    }
}
