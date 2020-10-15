package org.finalframework.data.query.enums;

import org.finalframework.annotation.data.Transient;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:45
 * @since 1.0
 */
@Transient
public enum UpdateOperation implements Operation {

    EQUAL,
    INC,
    INCR,
    DEC,
    DECR
}
