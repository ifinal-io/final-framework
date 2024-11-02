/*
 * Copyright 2020-2022 the original author or authors.
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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 枚举工具类
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@UtilityClass
public class Enums {

    /**
     * @param clazz     枚举类
     * @param creator   枚举创建者方法
     * @param valueType 值类型
     * @param value     值
     * @param <E>
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E extends Enum<E>> E findEnum(final @NonNull Class<? extends Enum> clazz,
                                                 final @NonNull String creator,
                                                 final @Nullable Class<?> valueType, final @NonNull Object value) {

        final Method valueOf = Objects.isNull(valueType) ? ReflectionUtils.findMethod(clazz, creator)
                : ReflectionUtils.findMethod(clazz, creator, valueType);

        if (valueOf == null) {
            throw new IllegalArgumentException(
                    "can not find method named " + creator + " as enum class " + clazz.getCanonicalName());
        }

        return (E) ReflectionUtils.invokeMethod(valueOf, clazz, value);
    }

    public static String findDesc(Enum<?> value, String desc) {
        Method method = Objects.requireNonNull(ReflectionUtils.findMethod(value.getClass(), desc), "can not found method named " + desc);
        Object descValue = ReflectionUtils.invokeMethod(method, value);
        return Objects.isNull(descValue) ? null : descValue.toString();
    }

    public static String getEnumCodeOfI18N(final Enum<?> value) {
        return String.format("%s.%s", value.getClass().getCanonicalName(), value.name().toLowerCase(Locale.ENGLISH));
    }

}

