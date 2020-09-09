package org.finalframework.annotation;

import org.finalframework.annotation.result.ResponseStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-25 13:29:07
 * @since 1.0
 */
public interface IResult<T> extends IData<T> {

    /**
     * return the status of this response.
     *
     * @return the status of this response.
     * @see ResponseStatus#getCode()
     */
    @NonNull
    Integer getStatus();

    /**
     * return the description of status.
     *
     * @return the description of status.
     * @see ResponseStatus#getDesc()
     */
    @NonNull
    String getDescription();


    /**
     * return the code of this response if response is not success.
     *
     * @return the code of this response if response is not success.
     * @see #getMessage()
     * @see IException#getCode()
     */
    @Nullable
    String getCode();

    /**
     * return the message for code.
     *
     * @return the message for code.
     * @see #getCode()
     * @see IException#getMessage()
     */
    @Nullable
    String getMessage();

    /**
     * return the {@linkplain IPagination pagination}
     *
     * @return the {@linkplain IPagination pagination}.
     */
    @Nullable
    IPagination getPagination();

    /**
     * return {@literal true} if this result response success, otherwise {@literal false}.
     *
     * @return {@literal true} if this result response success, otherwise {@literal false}.
     * @see ResponseStatus#SUCCESS
     */
    default boolean isSuccess() {
        return ResponseStatus.SUCCESS.getCode().equals(getStatus());
    }

    /**
     * return {@literal true} if has more data.
     *
     * @return {@literal true} if has more data.
     */
    default boolean hasMore() {
        return getPagination() != null && !Boolean.TRUE.equals(getPagination().getLastPage());
    }

}
