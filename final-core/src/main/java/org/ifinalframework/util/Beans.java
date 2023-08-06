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

package org.ifinalframework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Beans {

    private static final Map<Class<?>, BeanInfo> BEAN_INFO_MAP = new ConcurrentHashMap<>();

    private Beans() {
    }

    public static BeanInfo from(final Class<?> bean) {
        return BEAN_INFO_MAP.computeIfAbsent(bean, key -> {
            try {
                return Introspector.getBeanInfo(key);
            } catch (IntrospectionException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    public static Map<String, Object> toMap(final Object bean) {
        final BeanInfo beanInfo = from(bean.getClass());
        return Arrays.stream(beanInfo.getPropertyDescriptors())
                //.filter(propertyDescriptor -> propertyDescriptor.getName().equals("schema"))
                .collect(Collectors.toMap(PropertyDescriptor::getName, property -> {
                    try {
                        return property.getReadMethod().invoke(bean);
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }));
    }

}

