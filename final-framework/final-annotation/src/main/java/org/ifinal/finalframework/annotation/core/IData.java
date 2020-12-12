package org.ifinal.finalframework.annotation.core;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * A interface for data holder should be impl which provide the method of {@link #getData()}/{@link #setData(Object)} to
 * get/set the data which could be {@literal null}.
 *
 * @author likly
 * @version 1.0.0
 * @see IResult
 * @see IPage
 * @since 1.0.0
 */
public interface IData<T> extends Serializable {

    /**
     * return the data which may be {@literal null}.
     *
     * @return the data hold.
     */
    @Nullable
    T getData();

    /**
     * set the data which could be {@literal null}
     *
     * @param data the data to hold.
     */
    void setData(@Nullable T data);

}
