package org.finalframework.data.response;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:29:14
 * @since 1.0
 */
public interface Responsible {
    @NonNull
    Integer getStatus();

    void setStatus(@NonNull Integer status);

    String getDescription();

    @NonNull
    void setDescription(@NonNull String description);

}
