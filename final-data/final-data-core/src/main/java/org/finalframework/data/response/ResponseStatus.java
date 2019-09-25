package org.finalframework.data.response;

import lombok.Getter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:50:00
 * @since 1.0
 */
public enum ResponseStatus {
    SUCCESS(0, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;
    @Getter
    private final Integer code;
    @Getter
    private final String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}


