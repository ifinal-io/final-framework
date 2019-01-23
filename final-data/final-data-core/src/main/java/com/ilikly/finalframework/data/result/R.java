package com.ilikly.finalframework.data.result;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:11
 * @since 1.0
 */
public class R {

    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "success";

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static Result<?> failure(@NonNull Integer code, @NonNull String message) {
        return new Result<>(code, message);
    }


}
