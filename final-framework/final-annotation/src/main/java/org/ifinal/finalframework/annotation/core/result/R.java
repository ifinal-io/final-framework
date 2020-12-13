package org.ifinal.finalframework.annotation.core.result;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class R {

    private static final Integer SUCCESS_CODE = 0;

    private static final String SUCCESS_MESSAGE = "success";

    private R() {
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(final T data) {

        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(final @NonNull String code, final @NonNull String message,
        final @Nullable T data) {

        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, code, message, data);
    }

    @SuppressWarnings("rawtypes")
    public static Result failure(final @NonNull Integer status, final @NonNull String description,
        final @NonNull String code, final @NonNull String message) {

        return new Result<>(status, description, code, message);
    }

    @SuppressWarnings("rawtypes")
    public static Result failure(final @NonNull Integer status, final @NonNull String message) {

        return new Result<>(status, message);
    }

}
