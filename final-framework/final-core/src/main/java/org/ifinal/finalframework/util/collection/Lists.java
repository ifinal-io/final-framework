package org.ifinal.finalframework.util.collection;

import java.util.Arrays;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Lists {

    private Lists() {
    }

    @SafeVarargs
    public static <T> List<T> of(final T... elements) {
        return Arrays.asList(elements);
    }

}
