

package org.finalframework.data.response;

import org.finalframework.annotation.data.Transient;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:50:00
 * @since 1.0
 */
@Transient
public enum ResponseStatus {
    SUCCESS(0, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;
    private final Integer code;
    private final String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}


