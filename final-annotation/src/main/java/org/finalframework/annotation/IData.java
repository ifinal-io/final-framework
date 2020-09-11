package org.finalframework.annotation;

import org.springframework.lang.Nullable;

/**
 * A interface for data holder should be impl.
 *
 * @author likly
 * @version 1.0
 * @date 2020/9/9 17:57:02
 * @since 1.0
 */
public interface IData<T> {

    /**
     * set data
     *
     * @param data the data to hold.
     */
    void setData(@Nullable T data);

    /**
     * return the data hold.
     *
     * @return the data hold.
     */
    @Nullable
    T getData();
}
