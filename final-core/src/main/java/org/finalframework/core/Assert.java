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

package org.finalframework.core;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * General utility methods that assists in validating arguments.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:56
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface Assert {

    /**
     * Check whether the given {@code bool} is true.
     *
     * @param bool the candidate bool
     */
    static boolean isTrue(Boolean bool) {
        return bool != null && bool;
    }


    static void isTrue(Boolean bool, String message, Object... args) {
        if (isTrue(bool)) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    /**
     * Check whether the given {@code bool} is false.
     *
     * @param bool the candidate bool
     */
    static boolean isFalse(Boolean bool) {
        return bool != null && !bool;
    }

    static void isFalse(Boolean bool, String message, Object... args) {
        if (isFalse(bool)) throw new IllegalArgumentException(String.format(message, args));
    }

    /**
     * Check whether the given {@code obj} is null.
     *
     * @param obj the candidate Object.
     */
    static boolean isNull(Object obj) {
        return obj == null;
    }

    static void isNull(Object obj, String message, Object... args) {
        if (isNull(obj)) throw new NullPointerException(String.format(message, args));
    }

    /**
     * Check whether the given {@code obj} is not null.
     *
     * @param obj the candidate Object.
     */
    static boolean nonNull(Object obj) {
        return obj != null;
    }

    static void nonNull(Object obj, String message, Object... args) {
        if (nonNull(obj)) throw new NullPointerException(String.format(message, args));
    }

    /**
     * Check whether the given {@code obj} is empty.
     * If the given {@code obj} instanceof {@link String}, checked by {@link String#isEmpty()}.
     * If the given {@code obj} instanceof {@link Collection}, checked by {@link Collection#isEmpty()}.
     *
     * @param obj the candidate Object.
     * @see String#isEmpty()
     * @see Collection#isEmpty()
     */
    static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) return ((String) obj).isEmpty();
        if (obj instanceof Object[]) return ((Object[]) obj).length == 0;
        if (obj instanceof Collection) return ((Collection) obj).isEmpty();
        return false;
    }

    static void isEmpty(Object obj, String message, Object... args) {
        if (isEmpty(obj)) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    /**
     * Check whether the given {@code obj} is not empty.
     * If the given {@code obj} instanceof {@link String}, checked by {@link String#isEmpty()}.
     * If the given {@code obj} instanceof {@link Collection}, checked by {@link Collection#isEmpty()}.
     *
     * @param obj the candidate Object.
     * @see String#isEmpty()
     * @see Collection#isEmpty()
     */
    static boolean nonEmpty(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) return !((String) obj).isEmpty();
        if (obj instanceof Object[]) return ((Object[]) obj).length != 0;
        if (obj instanceof Collection) return !((Collection) obj).isEmpty();
        return true;
    }

    static void nonEmpty(Object obj, String message, Object... args) {
        if (nonEmpty(obj)) throw new IllegalArgumentException(String.format(message, args));
    }

    /**
     * Check whether the given {@code text} is blank.
     *
     * @param text the candidate String.
     */
    static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    static void isBlank(String obj, String message, Object... args) {
        if (isBlank(obj)) throw new IllegalArgumentException(String.format(message, args));
    }


    /**
     * Check whether the given {@code text} is not blank.
     *
     * @param text the candidate String.
     */
    static boolean nonBlank(String text) {
        return text != null && !text.trim().isEmpty();
    }

    static boolean isEqual(Object left, Object right) {
        return left != null && left.equals(right);
    }

    static boolean nonEqual(Object left, Object right) {
        return left != null && right != null && !left.equals(right);
    }

    @SuppressWarnings("unchecked")
    static <T extends Comparable> boolean isGreaterThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) > 0;
    }

    @SuppressWarnings("unchecked")
    static <T extends Comparable> boolean isGreaterEqualThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) >= 0;
    }

    @SuppressWarnings("unchecked")
    static <T extends Comparable> boolean isLessThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) < 0;
    }

    @SuppressWarnings("unchecked")
    static <T extends Comparable> boolean isLessEqualThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) <= 0;
    }

    static boolean isIn(Object candidate, Object... target) {
        return isIn(candidate, Arrays.asList(target));
    }

    static boolean isIn(Object candidate, Collection<?> target) {
        return nonNull(candidate) && nonEmpty(target) && target.contains(candidate);
    }

    static boolean nonIn(Object candidate, Object... target) {
        return nonIn(candidate, Arrays.asList(target));
    }

    static boolean nonIn(Object candidate, Collection<?> target) {
        return nonNull(candidate) && nonEmpty(target) && !target.contains(candidate);
    }


    @SuppressWarnings("unchecked")
    static <T extends Comparable> boolean isBetween(@NonNull T obj, @NonNull T min, @NonNull T max) {
        return obj.compareTo(min) > 0 && obj.compareTo(max) < 0;
    }

    @SuppressWarnings("unchecked")
    static <T extends Comparable> boolean nonBetween(@NonNull T obj, @NonNull T min, @NonNull T max) {
        return obj.compareTo(min) <= 0 || obj.compareTo(max) > 0;
    }


    /**
     * Check whether the given {@code text} is matches the given {@code regex}.
     *
     * @param text  the candidate String.
     * @param regex the regex String.
     */
    static boolean matches(@NonNull String text, @NonNull String regex) {
        return text.matches(regex);
    }


}
