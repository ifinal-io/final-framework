package org.ifinal.finalframework.data.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
class LimitImpl implements Limit, Serializable {

    private final Long offset;
    private final Long limit;

    LimitImpl(Long offset, Long limit) {
        this.offset = offset;
        this.limit = limit;
    }


}
