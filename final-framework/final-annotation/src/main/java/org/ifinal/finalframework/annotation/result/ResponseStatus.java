package org.ifinal.finalframework.annotation.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ifinal.finalframework.annotation.IEnum;
import org.ifinal.finalframework.annotation.data.Transient;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
@Getter
@AllArgsConstructor
public enum ResponseStatus implements IEnum<Integer> {
    SUCCESS(0, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;
    private final Integer code;
    private final String desc;

}


