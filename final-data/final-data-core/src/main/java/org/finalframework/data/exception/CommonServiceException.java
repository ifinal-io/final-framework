package org.finalframework.data.exception;

import lombok.Getter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:50:00
 * @since 1.0
 */
public enum CommonServiceException implements IException {
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found");
    @Getter
    private final Integer code;
    @Getter
    private final String message;

    CommonServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}


