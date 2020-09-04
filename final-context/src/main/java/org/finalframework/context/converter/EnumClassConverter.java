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

package org.finalframework.context.converter;

import org.finalframework.core.Assert;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-08 15:44:33
 * @since 1.0
 */
@SpringComponent
public class EnumClassConverter implements ClassConverter<Enum<?>, List<Map<String, Object>>> {

    private final Map<Class<?>, EnumConverter<?>> enumConverterMap = new HashMap<>();

    public EnumClassConverter(ObjectProvider<List<EnumConverter<?>>> enumConvertersProvider) {
        final List<EnumConverter<?>> enumConverters = enumConvertersProvider == null ? null : enumConvertersProvider.getIfAvailable();

        if (Assert.isEmpty(enumConverters)) {
            enumConverterMap.put(Enum.class, new IEnumConverter<>());
        } else {
            for (EnumConverter<?> enumConverter : enumConverters) {
                final EnumTarget annotation = enumConverter.getClass().getAnnotation(EnumTarget.class);
                enumConverterMap.put(annotation.value(), enumConverter);
            }
        }


    }

    @Override
    public List<Map<String, Object>> convert(Class<Enum<?>> source) {
        EnumConverter<?> converter = enumConverterMap.get(source);

        if (converter == null) {
            converter = enumConverterMap.get(Enum.class);
        }

        EnumConverter<Object> finalConverter = (EnumConverter<Object>) converter;
        return Arrays.stream(source.getEnumConstants())
                .map(finalConverter::convert)
                .collect(Collectors.toList());


    }
}
