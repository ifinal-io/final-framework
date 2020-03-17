package org.finalframework.core.filler;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 14:17:35
 * @since 1.0
 */
public interface FillerVisitor<H, P, R> {

    R fill(@NonNull H holder, @Nullable P param);
}
