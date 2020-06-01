package org.finalframework.data.query.operation.function;

import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 13:20:06
 * @since 1.0
 */
public interface Function extends Serializable {
    @NonNull
    String apply(@NonNull Object value);
}
