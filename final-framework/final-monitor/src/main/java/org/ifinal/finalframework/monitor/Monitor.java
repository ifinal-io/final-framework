package org.ifinal.finalframework.monitor;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Monitor {

    private Monitor() {
    }

    public static void alter(@NonNull String key, @NonNull String message) {
        // do noting
    }

    public static void notify(@NonNull String key, @NonNull String message) {
        // do nothing
    }

}
