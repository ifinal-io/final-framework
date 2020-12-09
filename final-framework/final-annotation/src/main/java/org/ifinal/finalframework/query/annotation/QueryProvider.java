package org.ifinal.finalframework.query.annotation;

import org.ifinal.finalframework.core.annotation.IEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface QueryProvider {
    @NonNull
    String provide(@Nullable String expression, @NonNull Class<? extends IEntity<?>> entity, @NonNull Class<?> query);
}
