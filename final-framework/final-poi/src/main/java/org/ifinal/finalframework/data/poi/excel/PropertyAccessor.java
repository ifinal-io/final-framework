package org.ifinal.finalframework.data.poi.excel;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * PropertyAccessor.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface PropertyAccessor<T, R> {

    @Nullable
    R read(@NonNull String expression,@Nullable T context);

}
