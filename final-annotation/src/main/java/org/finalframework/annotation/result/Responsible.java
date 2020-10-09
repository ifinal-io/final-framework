package org.finalframework.annotation.result;

import org.springframework.lang.NonNull;

/**
 * The interface of the response which should be have the response {@linkplain #getStatus() status} and {@linkplain #getDescription() description}.
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:29:14
 * @since 1.0
 */
public interface Responsible {
    /**
     * return the code of this response
     *
     * @return the response code
     * @see ResponseStatus#getCode()
     */
    @NonNull
    Integer getStatus();

    /**
     * return the description of this response.
     *
     * @return the response description.
     * @see ResponseStatus#getDesc()
     */
    @NonNull
    String getDescription();
}
