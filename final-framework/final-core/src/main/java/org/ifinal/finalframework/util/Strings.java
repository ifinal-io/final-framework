package org.ifinal.finalframework.util;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Strings {

    private static final String BLANK = "";

    private Strings() {
    }

    public static String join(final @NonNull Collection<?> collection, final @NonNull String delimiter) {
        return join(collection, delimiter, null, null);
    }

    public static String join(final @NonNull Collection<?> collection, final @NonNull String delimiter, final @Nullable String prefix,
        final @Nullable String suffix) {
        return collection.stream()
            .map(Object::toString)
            .collect(Collectors.joining(delimiter, Optional.ofNullable(prefix).orElse(BLANK), Optional.ofNullable(suffix).orElse(BLANK)));
    }

    public static String replaceFirst(final @NonNull String text, final @NonNull String source, final @NonNull String target) {
        return text.replaceFirst(source, target);
    }

    public static String replaceLast(final @NonNull String text, final @NonNull String source, final @NonNull String target) {
        final int index = text.lastIndexOf(source);
        if (index != -1) {
            return text.substring(0, index) + target;
        }
        return text;
    }

    public static String[] split(final @NonNull String text, final @NonNull String delimiter) {
        return split(text, delimiter, null, null);
    }

    public static String[] split(final @NonNull String text, final @NonNull String delimiter, final String open, final String close) {
        String str = text;

        if (open != null) {
            str = str.replaceFirst(open, "");
        }
        if (close != null) {
            str = replaceLast(str, close, "");
        }
        return str.split(delimiter);
    }

}
