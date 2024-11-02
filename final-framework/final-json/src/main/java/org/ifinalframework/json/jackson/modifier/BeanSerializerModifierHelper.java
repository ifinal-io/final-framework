/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.json.jackson.modifier;


import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * BeanSerializerModifierHelper
 *
 * @author iimik
 * @since 1.6.0
 **/
@UtilityClass
public class BeanSerializerModifierHelper {

    /**
     * @see BeanPropertyWriter#getName()
     * @see SerializedString
     */
    private static final Field NAME_FIELD = Objects
            .requireNonNull(ReflectionUtils.findField(BeanPropertyWriter.class, "_name"));

    static {
        ReflectionUtils.makeAccessible(NAME_FIELD);
    }

    /**
     * 设置属性名{@link BeanPropertyWriter#getName()}
     *
     * @param bpw
     * @param value
     */
    public static void setPropertyName(final BeanPropertyWriter bpw, final String value) {
        ReflectionUtils.setField(NAME_FIELD, bpw, new SerializedString(value));
    }

}
