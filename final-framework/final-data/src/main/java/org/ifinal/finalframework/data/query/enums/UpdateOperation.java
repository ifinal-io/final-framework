package org.ifinal.finalframework.data.query.enums;

import org.ifinal.finalframework.data.query.operation.Operation;
import org.ifinal.finalframework.origin.lang.Transient;

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
