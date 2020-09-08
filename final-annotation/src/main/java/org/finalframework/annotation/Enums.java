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

package org.finalframework.annotation;


import org.finalframework.annotation.data.EnumValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:18:59
 * @since 1.0
 */
public interface Enums {

    static <E extends Enum<E>> E findEnum(EnumValue ann, Object value) {
        return findEnum(ann.value(), ann.creator(), ann.valueType(), value);
    }

    @SuppressWarnings("unchecked")
    static <E extends Enum<E>> E findEnum(Class<? extends Enum<?>> clazz, String creator, Class<?> valueType, Object value) {
        Method valueOf = ReflectionUtils.findMethod(clazz, creator, valueType);

        if (valueOf == null) {
            throw new IllegalArgumentException("can not find method named " + creator + " with type of " + valueType.getCanonicalName() + " as enum class " + clazz.getCanonicalName());
        }

        return (E) ReflectionUtils.invokeMethod(valueOf, clazz, value);
    }

    static String getEnumI18NCode(Enum<?> value) {
        return String.format("%s.%s", value.getClass().getCanonicalName(), value.name().toLowerCase(Locale.ENGLISH));
    }


}

