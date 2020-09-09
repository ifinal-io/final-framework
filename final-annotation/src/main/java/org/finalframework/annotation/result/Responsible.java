

package org.finalframework.annotation.result;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:29:14
 * @since 1.0
 */
public interface Responsible {
    /**
     * 响应状态码
     *
     * @see ResponseStatus#getCode()
     */
    @NonNull
    Integer getStatus();

    String getDescription();

}
