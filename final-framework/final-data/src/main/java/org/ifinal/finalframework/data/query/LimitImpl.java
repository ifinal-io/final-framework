package org.ifinal.finalframework.data.query;

import java.io.Serializable;

import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
class LimitImpl implements Limit, Serializable {

    private final Long offset;

    private final Long limit;

    LimitImpl(final Long offset, final Long limit) {

        this.offset = offset;
        this.limit = limit;
    }

}
