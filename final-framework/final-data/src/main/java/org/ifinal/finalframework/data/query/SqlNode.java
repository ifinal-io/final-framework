package org.ifinal.finalframework.data.query;


import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface SqlNode {
    void apply(@NonNull StringBuilder sql, @NonNull String expression);
}
