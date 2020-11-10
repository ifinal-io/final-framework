package org.finalframework.annotation.query;

import org.finalframework.annotation.IEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 10:08:38
 * @since 1.0
 */
@FunctionalInterface
public interface QueryProvider {
    @NonNull
    String provide(@Nullable String expression, @NonNull Class<? extends IEntity<?>> entity, @NonNull Class<?> query);
}
