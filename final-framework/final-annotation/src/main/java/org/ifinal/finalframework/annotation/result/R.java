package org.ifinal.finalframework.annotation.result;

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

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(@NonNull String code, @NonNull String message, @Nullable T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, code, message, data);
    }

    public static Result<?> failure(@NonNull Integer status, @NonNull String description, @NonNull String code, @NonNull String message) {
        return new Result<>(status, description, code, message);
    }


    public static Result<?> failure(@NonNull Integer status, @NonNull String message) {
        return new Result<>(status, message);
    }

}
