package org.ifinal.finalframework.data.query.enums;

import org.ifinal.finalframework.data.annotation.Transient;
import org.ifinal.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
public enum UpdateOperation implements Operation {

    EQUAL,
    INC,
    INCR,
    DEC,
    DECR
}
