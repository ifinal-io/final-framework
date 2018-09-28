package cn.com.likly.finalframework.context.result;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:11
 * @since 1.0
 */
public class R {

    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "success";


    public static <T extends Serializable> Result<T> success() {
        return success(null);
    }

    public static <T extends Serializable> Result<T> success(T data) {
        return success(data, UUID.randomUUID().toString());
    }

    public static <T extends Serializable> Result<T> success(@NonNull String trace) {
        return success(trace, System.currentTimeMillis());
    }

    public static <T extends Serializable> Result<T> success(T data, @NonNull String trace) {
        return success(data, trace, System.currentTimeMillis());
    }

    public static <T extends Serializable> Result<T> success(@NonNull String trace, @NonNull Long timestamp) {
        return success(null, trace, timestamp);
    }

    public static <T extends Serializable> Result<T> success(T data, @NonNull String trace, @NonNull Long timestamp) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data, trace, timestamp);
    }

    public static Result<?> failure(@NonNull Integer code, @NonNull String message) {
        return failure(code, message, UUID.randomUUID().toString());
    }

    public static Result<?> failure(@NonNull Integer code, @NonNull String message, @NonNull String trace) {
        return failure(code, message, trace, System.currentTimeMillis());
    }

    public static Result<?> failure(@NonNull Integer code, @NonNull String message, @NonNull String trace, @NotNull Long timestamp) {
        return new Result<>(code, message, null, trace, timestamp);
    }

}
