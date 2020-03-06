package org.finalframework.core.filler;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 14:41:59
 * @since 1.0
 */
public interface Filler<H, R> {
    R fill(@NonNull H holder);
}
