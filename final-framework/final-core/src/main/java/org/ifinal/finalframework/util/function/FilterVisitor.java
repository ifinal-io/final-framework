package org.ifinal.finalframework.util.function;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface FilterVisitor<T, P> {
    boolean matches(@NonNull T t, @Nullable P param);
}
