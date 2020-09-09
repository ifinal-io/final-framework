

package org.finalframework.annotation.data;


import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-28 18:21:29
 * @since 1.0
 */
public final class SqlKeyWords {
    private static final Set<String> keys = new HashSet<>();

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

