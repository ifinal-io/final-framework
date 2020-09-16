package org.finalframework.data.query;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 17:13:34
 * @since 1.0
 */
public class LimitImpl implements Limit, Serializable {

    private Long offset;
    private Long limit;

    public LimitImpl(Long offset, Long limit) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Override
    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
