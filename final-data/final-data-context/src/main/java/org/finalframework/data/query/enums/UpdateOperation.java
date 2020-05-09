package org.finalframework.data.query.enums;

import org.finalframework.data.annotation.Transient;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:45
 * @since 1.0
 */
@Transient
public enum UpdateOperation {

    EQUAL,
    INC,
    INCR,
    DEC,
    DECR
}
