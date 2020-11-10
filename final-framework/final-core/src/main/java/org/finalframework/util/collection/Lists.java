package org.finalframework.util.collection;

import java.util.Arrays;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/28 11:02:22
 * @since 1.0
 */
public final class Lists {
    private Lists() {
    }


    @SafeVarargs
    public static <T> List<T> of(T... elements) {
        return Arrays.asList(elements);
    }
}
