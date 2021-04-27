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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.slf4j.helpers.MessageFormatter;

/**
 * General utility methods that asserts in validating arguments.
 *
 * @author likly
 * @version 1.0.0
 * @see MessageFormatter#arrayFormat(String, Object[])
 * @see Objects
 * @see ObjectUtils
 * @see org.springframework.util.CollectionUtils
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public final class Asserts {

    private Asserts() {
    }

    public static boolean isNull(final Object obj) {
        return Objects.isNull(obj);
    }

    public static void requiredNull(final Object obj) {
        requiredNull(obj, "required null but found {}", obj);
    }

    public static void requiredNull(final Object obj, final String message, final Object... args) {
        if (!isNull(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean nonNull(final Object obj) {
        return Objects.nonNull(obj);
    }

    public static <T> T requiredNonNull(final T obj) {
        return Objects.requireNonNull(obj);
    }

    public static <T> T requiredNonNull(final T obj, final String message, final Object... args) {
        return Objects.requireNonNull(obj, formatMessage(message, args));
    }

    public static boolean isTrue(final Object bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean requiredTrue(final Object bool) {
        return requiredTrue(bool, "required true value but found {}", bool);
    }

    public static boolean requiredTrue(final Object bool, @NonNull final String message, final Object... args) {
        if (!isTrue(bool)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
        return true;
    }

    public static boolean isFalse(final Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean requiredFalse(final Object bool) {
        return requiredFalse(bool, "required false but found {}", bool);
    }

    public static boolean requiredFalse(final Object bool, final String message, final Object... args) {
        if (Boolean.FALSE.equals(bool)) {
            return false;
        }

        throw new IllegalArgumentException(formatMessage(message, args));
    }

    /**
     * @see org.springframework.util.ObjectUtils#isEmpty(Object)
     */
    public static boolean isEmpty(@Nullable final Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    public static void isEmpty(final Object obj, final String message, final Object... args) {
        if (isEmpty(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean nonEmpty(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return !((String) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length != 0;
        }
        if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return !((Map<?, ?>) obj).isEmpty();
        }

        return true;
    }

    public static <T> T requiredNonEmpty(final T obj) {
        return requiredNonEmpty(obj, "required not empty but found {}", obj);
    }

    public static <T> T requiredNonEmpty(final T obj, final String message, final Object... args) {

        if (isEmpty(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }

        return obj;
    }

    public static boolean isBlank(final String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean nonBlank(final String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static String requiredNonBlank(final String text) {
        return requiredNonBlank(text, "required not blank but found {}", text);
    }

    public static String requiredNonBlank(final String text, final String message, final Object... args) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }

        return text;
    }

    public static boolean isEquals(final Object left, final Object right) {
        return Objects.equals(left, right);
    }

    public static boolean nonEquals(final Object left, final Object right) {
        return !Objects.equals(left, right);
    }

    public static boolean matches(final @NonNull String text, final @NonNull String regex) {
        return text.matches(regex);
    }

    private static String formatMessage(final String message, final Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

}
