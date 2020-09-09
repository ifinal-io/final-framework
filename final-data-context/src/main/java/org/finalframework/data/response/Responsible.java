

package org.finalframework.data.response;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:29:14
 * @since 1.0
 */
public interface Responsible {
    /**
     * return the response code
     *
     * @return the response code
     * @see ResponseStatus#getCode()
     */
    @NonNull
    Integer getStatus();

    /**
     * return the response description
     *
     * @return the response description
     */
    String getDescription();

}
