package org.finalframework.annotation.data;

import org.finalframework.annotation.query.Metadata;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 09:43:59
 * @since 1.0
 */
public interface ColumnHandler {

    String handleWriter(@NonNull Column column, @NonNull Metadata metadata);

    String handleReader(@NonNull Column column, @NonNull Metadata metadata);
}
