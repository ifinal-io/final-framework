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

import org.slf4j.helpers.MessageFormatter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * General utility methods that asserts in validating arguments.
 *
 * @author ilikly
 * @version 1.0.0
 * @see MessageFormatter#arrayFormat(String, Object[])
 * @see Objects
 * @see ObjectUtils
 * @see org.springframework.util.CollectionUtils
 * @since 1.0.0
 */
@UtilityClass
public final class Asserts {


    /**
     * return {@code true} if the test object is null.
     *
     * @param obj test object.
     */
    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * @see #requiredNonNull(Object, String, Object...)
     */
    public static void requiredNull(Object obj) {
        requiredNull(obj, "the value required must be null but found {}", obj);
    }

    /**
     * Assert that an object is null.
     * <pre class="code">
     *     Asserts.requiredNull(value,"the {} required muse be null.","value")
     * </pre>
     *
     * @param obj     the object to check.
     * @param message the exception message to use if the assertion fails.
     * @param args    the exception message format args.
     * @throws IllegalArgumentException if the object is not null.
     */
    public static void requiredNull(Object obj, String message, Object... args) {
        if (!isNull(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean nonNull(Object obj) {
        return Objects.nonNull(obj);
    }

    public static <T> T requiredNonNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    /**
     * Assert that an object is not null.
     *
     * @param obj     the object to check.
     * @param message the exception message to use if the assertion fails.
     * @param args    the exception message format args.
     * @param <T>     the object type.
     * @throws NullPointerException is the object is null.
     */
    public static <T> T requiredNonNull(T obj, String message, Object... args) {
        return Objects.requireNonNull(obj, formatMessage(message, args));
    }

    /**
     * Assert that an object is {@code true}.
     *
     * @param bool the object to check.
     */
    public static boolean isTrue(Object bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean requiredTrue(Object bool) {
        return requiredTrue(bool, "required true value but found {}", bool);
    }

    /**
     * Assert that an object is {@code true}.
     *
     * @param bool    the object to check.
     * @param message the exception message to use if the assertion fails.
     * @param args    the exception message format args.
     * @throws IllegalArgumentException if the object is not {@code true}.
     */
    public static boolean requiredTrue(Object bool, @NonNull String message, Object... args) {
        if (!isTrue(bool)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
        return true;
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean requiredFalse(Object bool) {
        return requiredFalse(bool, "required false but found {}", bool);
    }

    /**
     * Assert that an object is {@code false}.
     *
     * @param bool    the object to check.
     * @param message the exception message to use if the assertion fails.
     * @param args    the exception message format args.
     * @throws IllegalArgumentException if the object is not {@code false}.
     */
    public static boolean requiredFalse(Object bool, String message, Object... args) {
        if (Boolean.FALSE.equals(bool)) {
            return false;
        }

        throw new IllegalArgumentException(formatMessage(message, args));
    }

    /**
     * @see org.springframework.util.ObjectUtils#isEmpty(Object)
     */
    public static boolean isEmpty(@Nullable Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    /**
     * Assert that an object is empty.
     *
     * @param obj     the object to check.
     * @param message the exception message to use if the assertion fails.
     * @param args    the exception message format args.
     * @throws IllegalArgumentException if the object is empty.
     * @see #isEmpty(Object)
     */
    public static void isEmpty(Object obj, String message, Object... args) {
        if (isEmpty(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean nonEmpty(Object obj) {
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
            return !((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return !((Map<?, ?>) obj).isEmpty();
        }

        return true;
    }

    public static <T> T requiredNonEmpty(T obj) {
        return requiredNonEmpty(obj, "required not empty but found {}", obj);
    }

    public static <T> T requiredNonEmpty(T obj, String message, Object... args) {

        if (isEmpty(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }

        return obj;
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean nonBlank(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static String requiredNonBlank(String text) {
        return requiredNonBlank(text, "required not blank but found {}", text);
    }

    public static String requiredNonBlank(String text, String message, Object... args) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }

        return text;
    }

    public static boolean isEquals(Object left, Object right) {
        return Objects.equals(left, right);
    }

    public static boolean nonEquals(Object left, Object right) {
        return !Objects.equals(left, right);
    }

    public static boolean matches(@NonNull String text, @NonNull String regex) {
        return text.matches(regex);
    }

    private static String formatMessage(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

}
