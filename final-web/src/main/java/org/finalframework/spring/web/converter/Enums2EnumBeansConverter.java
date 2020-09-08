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

import org.finalframework.annotation.Enums;
import org.finalframework.annotation.IEnum;
import org.finalframework.context.util.Messages;
import org.finalframework.core.Assert;
import org.finalframework.core.converter.Converter;
import org.finalframework.core.filter.Filter;
import org.finalframework.spring.web.response.advice.EnumsResponseBodyAdvice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 将实现了{@link IEnum}接口的枚举对象集转换成{@link Map<String,Object>}对象集。
 * <p>
 * 在调用{@link #convert(Object)}方法之前，一定要先调用 {@link #matches(Object)} 方法判断是否匹配。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-27 10:19:24
 * @see EnumsResponseBodyAdvice
 * @since 1.0
 */
public class Enums2EnumBeansConverter implements Converter<Object, List<Map<String, Object>>>, Filter<Object> {

    @Override
    public boolean matches(Object enums) {


        if (Assert.isNull(enums)) return false;

        if (enums.getClass().isArray()) {
            if (Assert.isEmpty(enums)) return false;
            return ((Object[]) enums)[0] instanceof IEnum;
        }

        if (enums instanceof List) {
            if (Assert.isEmpty(enums)) return false;
            return ((List<?>) enums).get(0) instanceof IEnum;
        }

        if (enums instanceof Set) {
            if (Assert.isEmpty(enums)) return false;
            return ((Set<?>) enums).toArray()[0] instanceof IEnum;
        }

        if (enums instanceof Class && IEnum.class.isAssignableFrom((Class<?>) enums)) {
            return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> convert(Object body) {

        if (body instanceof Collection) {
            Collection<IEnum<?>> enums = (Collection<IEnum<?>>) body;
            return enums.stream().map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body.getClass().isArray()) {
            IEnum<?>[] enums = (IEnum<?>[]) body;
            return Arrays.stream(enums).map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body instanceof Class && IEnum.class.isAssignableFrom((Class<?>) body)) {
            final Class<Enum> enumClass = (Class<Enum>) body;
            return Arrays.stream(enumClass.getEnumConstants())
                    .map(ienum -> this.buildEnumBean((IEnum<?>) ienum))
                    .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("不支持的数据类型：" + body.getClass());
    }

    private Map<String, Object> buildEnumBean(IEnum<?> ienum) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ienum.getCode());
        if (ienum instanceof Enum) {
            result.put("desc", Messages.getMessage(Enums.getEnumI18NCode((Enum<?>) ienum), ienum.getDesc()));
            result.put("name", ((Enum<?>) ienum).name());
        } else {
            result.put("desc", ienum.getDesc());
        }
        return result;
    }

}
