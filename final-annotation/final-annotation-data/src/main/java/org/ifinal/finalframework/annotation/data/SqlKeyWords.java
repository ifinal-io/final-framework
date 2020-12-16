package org.ifinal.finalframework.annotation.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SqlKeyWords {

    private static final Set<String> keys = new HashSet<>();

    static {
        init("key", "order", "group", "source");
    }

    private SqlKeyWords() {
    }

    private static void init(final String... keys) {

        Arrays.stream(keys).map(String::toLowerCase).forEach(SqlKeyWords.keys::add);
    }

    public static boolean contains(final @NonNull String word) {

        return keys.contains(word.toLowerCase());
    }

}

