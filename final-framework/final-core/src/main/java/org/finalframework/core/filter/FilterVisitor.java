package org.finalframework.core.filter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 14:37:49
 * @since 1.0
 */
public interface FilterVisitor<T, P> {
    boolean matches(@NonNull T t, @Nullable P param);
}
