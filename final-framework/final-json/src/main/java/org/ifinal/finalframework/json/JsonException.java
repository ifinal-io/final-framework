package org.ifinal.finalframework.json;

/**
 * warp the {@link Throwable} throw by {@link JsonService} on the {@link Json}.
 *
 * @author likly
 * @version 1.0.0
 * @see Json
 * @see JsonService
 * @since 1.0.0
 */
public class JsonException extends RuntimeException {

    public JsonException(final Throwable cause) {

        super(cause);
    }

}
