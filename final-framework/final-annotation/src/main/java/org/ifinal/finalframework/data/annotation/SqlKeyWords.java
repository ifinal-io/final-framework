package org.ifinal.finalframework.data.annotation;


import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SqlKeyWords {
    private static final Set<String> keys = new HashSet<>();

    private SqlKeyWords() {
    }

    static {
        init("key", "order", "group", "source");
    }

    private static void init(String... keys) {
        Arrays.stream(keys).map(String::toLowerCase).forEach(SqlKeyWords.keys::add);
    }

    public static boolean contains(@NonNull String word) {
        return keys.contains(word.toLowerCase());
    }
}

