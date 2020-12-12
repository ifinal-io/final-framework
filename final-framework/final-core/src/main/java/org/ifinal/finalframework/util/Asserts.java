package org.ifinal.finalframework.util;


import org.slf4j.helpers.MessageFormatter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * General utility methods that asserts in validating arguments.
 *
 * @author likly
 * @version 1.0.0
 * @see MessageFormatter#arrayFormat(String, Object[])
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public final class Asserts {

    private Asserts() {
    }

    public static boolean isTrue(final Boolean bool) {

        return Boolean.TRUE.equals(bool);
    }


    public static void isTrue(final Boolean bool, final @NonNull String message, final Object... args) {

        if (isTrue(bool)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean isFalse(final Boolean bool) {

        return Boolean.FALSE.equals(bool);
    }

    public static void isFalse(final Boolean bool, final String message, final Object... args) {

        if (isFalse(bool)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean isNull(final Object obj) {

        return Objects.isNull(obj);
    }

    public static void isNull(final Object obj, final String message, final Object... args) {

        Assert.notNull(obj, formatMessage(message, args));
    }

    public static boolean nonNull(final Object obj) {

        return Objects.nonNull(obj);
    }

    public static void nonNull(final Object obj, final String message, final Object... args) {

        if (nonNull(obj)) {
            throw new NullPointerException(formatMessage(message, args));
        }
    }

    public static boolean isEmpty(final Object obj) {

        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
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

    public static void nonEmpty(final Object obj, final String message, final Object... args) {

        if (nonEmpty(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean isBlank(final String text) {

        return text == null || text.trim().isEmpty();
    }

    public static void isBlank(final String obj, final String message, final Object... args) {

        if (isBlank(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }


    public static boolean nonBlank(final String text) {

        return text != null && !text.trim().isEmpty();
    }

    public static boolean isEqual(final Object left, final Object right) {

        return left != null && left.equals(right);
    }

    public static boolean nonEqual(final Object left, final Object right) {

        return left != null && right != null && !left.equals(right);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isGreaterThan(final T candidate, final T target) {

        return candidate != null && target != null && candidate.compareTo(target) > 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isGreaterEqualThan(final T candidate, final T target) {

        return candidate != null && target != null && candidate.compareTo(target) >= 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isLessThan(final T candidate, final T target) {

        return candidate != null && target != null && candidate.compareTo(target) < 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isLessEqualThan(final T candidate, final T target) {

        return candidate != null && target != null && candidate.compareTo(target) <= 0;
    }

    public static boolean isIn(final Object candidate, final Object... target) {

        return isIn(candidate, Arrays.asList(target));
    }

    public static boolean isIn(final Object candidate, final Collection<?> target) {

        return nonNull(candidate) && nonEmpty(target) && target.contains(candidate);
    }

    public static boolean nonIn(final Object candidate, final Object... target) {

        return nonIn(candidate, Arrays.asList(target));
    }

    public static boolean nonIn(final Object candidate, final Collection<?> target) {

        return nonNull(candidate) && nonEmpty(target) && !target.contains(candidate);
    }


    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isBetween(final @NonNull T obj, final @NonNull T min, final @NonNull T max) {

        return obj.compareTo(min) > 0 && obj.compareTo(max) < 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean nonBetween(final @NonNull T obj, final @NonNull T min, final @NonNull T max) {

        return obj.compareTo(min) <= 0 || obj.compareTo(max) > 0;
    }


    public static boolean matches(final @NonNull String text, final @NonNull String regex) {

        return text.matches(regex);
    }

    private static String formatMessage(final String message, final Object... args) {

        return MessageFormatter.arrayFormat(message, args).getMessage();
    }


}
