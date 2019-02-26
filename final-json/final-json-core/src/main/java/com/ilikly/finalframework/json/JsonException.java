package com.ilikly.finalframework.json;

/**
 * warp the {@link Throwable} throw by {@link JsonService} on the {@link Json}.
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:29
 * @since 1.0
 * @see Json
 * @see JsonService
 */
public class JsonException extends RuntimeException {
    public JsonException(Throwable cause) {
        super(cause);
    }
}
