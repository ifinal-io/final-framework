package org.finalframework.util;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 13:35
 * @since 1.0
 */
public final class Strings {

    private static final String BLANK = "";

    private Strings() {
    }

    public static String join(@NonNull Collection<?> collection, @NonNull String delimiter) {
        return join(collection, delimiter, null, null);
    }

    public static String join(@NonNull Collection<?> collection, @NonNull String delimiter, @Nullable String prefix, @Nullable String suffix) {
        return collection.stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimiter, Optional.ofNullable(prefix).orElse(BLANK), Optional.ofNullable(suffix).orElse(BLANK)));
    }

    public static String replaceFirst(@NonNull String text, @NonNull String source, @NonNull String target) {
        return text.replaceFirst(source, target);
    }

    public static String replaceLast(@NonNull String text, @NonNull String source, @NonNull String target) {
        final int index = text.lastIndexOf(source);
        if (index != -1) {
            return text.substring(0, index) + target;
        }
        return text;
    }

    public static String[] split(@NonNull String text, @NonNull String delimiter) {
        return split(text, delimiter, null, null);
    }

    public static String[] split(@NonNull String text, @NonNull String delimiter, String open, String close) {
        if (open != null) {
            text = text.replaceFirst(open, "");
        }
        if (close != null) {
            text = replaceLast(text, close, "");
        }
        return text.split(delimiter);
    }
}
