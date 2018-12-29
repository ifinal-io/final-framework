package com.ilikly.finalframework.util;

import lombok.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 13:35
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface StringUtils {


    static String join(@NonNull Collection<?> collection, @NonNull String separator) {
        return join(collection, separator, null, null);
    }

    static String join(@NonNull Collection<?> collection, @NonNull String separator, String open, String close) {
        final StringBuilder sb = new StringBuilder();

        if (open != null) {
            sb.append(open);
        }

        int i = 0;
        for (Object item : collection) {
            if (i++ != 0) {
                sb.append(separator);
            }
            sb.append(item.toString());
        }

        if (close != null) {
            sb.append(close);
        }

        return sb.toString();
    }

    static String replaceFirst(@NonNull String text, @NonNull String source, @NonNull String target) {
        return text.replaceFirst(source, target);
    }

    static String replaceLast(@NonNull String text, @NonNull String source, @NonNull String target) {
        final int index = text.lastIndexOf(source);
        if (index != -1) {
            return text.substring(0, index) + target;
        }
        return text;
    }

    static String[] split(@NonNull String text, @NonNull String separator) {
        return split(text, separator, null, null);
    }

    static String[] split(@NonNull String text, @NonNull String separator, String open, String close) {
        if (open != null) {
            text = text.replaceFirst(open, "");
        }
        if (close != null) {
            text = replaceLast(text, close, "");
        }
        return text.split(separator);
    }
}
