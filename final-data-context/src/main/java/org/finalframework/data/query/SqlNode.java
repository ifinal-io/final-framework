

package org.finalframework.data.query;


import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-04 16:07:03
 * @since 1.0
 */
@FunctionalInterface
public interface SqlNode {
    void apply(@NonNull StringBuilder sql, @NonNull String expression);
}
