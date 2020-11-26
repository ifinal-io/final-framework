package org.ifinal.finalframework.util;


import org.slf4j.helpers.MessageFormatter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
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

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }


    public static void isTrue(Boolean bool, @NonNull String message, Object... args) {
        if (isTrue(bool)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static void isFalse(Boolean bool, String message, Object... args) {
        if (isFalse(bool)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    public static void isNull(Object obj, String message, Object... args) {
        Assert.notNull(obj, formatMessage(message, args));
    }

    public static boolean nonNull(Object obj) {
        return Objects.nonNull(obj);
    }

    public static void nonNull(Object obj, String message, Object... args) {
        if (nonNull(obj)) {
            throw new NullPointerException(formatMessage(message, args));
        }
    }

    public static boolean isEmpty(Object obj) {
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
        return false;
    }

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
            return !((Collection) obj).isEmpty();
        }
        return true;
    }

    public static void nonEmpty(Object obj, String message, Object... args) {
        if (nonEmpty(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static void isBlank(String obj, String message, Object... args) {
        if (isBlank(obj)) {
            throw new IllegalArgumentException(formatMessage(message, args));
        }
    }


    public static boolean nonBlank(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isEqual(Object left, Object right) {
        return left != null && left.equals(right);
    }

    public static boolean nonEqual(Object left, Object right) {
        return left != null && right != null && !left.equals(right);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isGreaterThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) > 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isGreaterEqualThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) >= 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isLessThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) < 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isLessEqualThan(T candidate, T target) {
        return candidate != null && target != null && candidate.compareTo(target) <= 0;
    }

    public static boolean isIn(Object candidate, Object... target) {
        return isIn(candidate, Arrays.asList(target));
    }

    public static boolean isIn(Object candidate, Collection<?> target) {
        return nonNull(candidate) && nonEmpty(target) && target.contains(candidate);
    }

    public static boolean nonIn(Object candidate, Object... target) {
        return nonIn(candidate, Arrays.asList(target));
    }

    public static boolean nonIn(Object candidate, Collection<?> target) {
        return nonNull(candidate) && nonEmpty(target) && !target.contains(candidate);
    }


    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean isBetween(@NonNull T obj, @NonNull T min, @NonNull T max) {
        return obj.compareTo(min) > 0 && obj.compareTo(max) < 0;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> boolean nonBetween(@NonNull T obj, @NonNull T min, @NonNull T max) {
        return obj.compareTo(min) <= 0 || obj.compareTo(max) > 0;
    }


    public static boolean matches(@NonNull String text, @NonNull String regex) {
        return text.matches(regex);
    }

    private static String formatMessage(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }


}
