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

package org.ifinalframework.web.converter;

import org.ifinalframework.context.util.Messages;
import org.ifinalframework.core.IEnum;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.util.Enums;
import org.ifinalframework.util.function.Converter;
import org.ifinalframework.util.function.Filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Enums2EnumBeansConverter implements Converter<Object, List<Map<String, Object>>>, Filter<Object> {

    @Override
    public boolean matches(final Object enums) {

        if (Asserts.isNull(enums)) {
            return false;
        }

        if (enums.getClass().isArray()) {
            if (Asserts.isEmpty(enums)) {
                return false;
            }
            return ((Object[]) enums)[0] instanceof IEnum;
        }

        if (enums instanceof List) {
            if (Asserts.isEmpty(enums)) {
                return false;
            }
            return ((List<?>) enums).get(0) instanceof IEnum;
        }

        if (enums instanceof Set) {
            if (Asserts.isEmpty(enums)) {
                return false;
            }
            return ((Set<?>) enums).toArray()[0] instanceof IEnum;
        }

        return enums instanceof Class && IEnum.class.isAssignableFrom((Class<?>) enums);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> convert(final Object body) {

        if (body instanceof Collection) {
            Collection<IEnum<?>> enums = (Collection<IEnum<?>>) body;
            return enums.stream().map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body.getClass().isArray()) {
            IEnum<?>[] enums = (IEnum<?>[]) body;
            return Arrays.stream(enums).map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body instanceof Class && IEnum.class.isAssignableFrom((Class<?>) body)) {
            final Class<Enum<?>> enumClass = (Class<Enum<?>>) body;
            return Arrays.stream(enumClass.getEnumConstants())
                .map(item -> this.buildEnumBean((IEnum<?>) item))
                .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("不支持的数据类型：" + body.getClass());
    }

    private Map<String, Object> buildEnumBean(final IEnum<?> item) {

        Map<String, Object> result = new HashMap<>();
        result.put("code", item.getCode());
        if (item instanceof Enum) {
            result.put("desc", Messages.getMessage(Enums.getEnumI18NCode((Enum<?>) item), item.getDesc()));
            result.put("name", ((Enum<?>) item).name());
        } else {
            result.put("desc", item.getDesc());
        }
        return result;
    }

}
