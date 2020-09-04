package org.finalframework.data.annotation;

import org.finalframework.data.annotation.result.ResponseStatus;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-25 13:29:07
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface IResult<T> {

    Integer getStatus();

    String getCode();

    String getMessage();

    @Nullable
    T getData();

    @Nullable
    IPagination getPagination();

    default boolean isSuccess() {
        return ResponseStatus.SUCCESS.getCode().equals(getStatus());
    }

    default boolean hasMore() {
        return getPagination() != null && !Boolean.TRUE.equals(getPagination().getLastPage());
    }

}
