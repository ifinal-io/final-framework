package org.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 21:21:40
 * @since 1.0
 */
public interface Query extends Pageable {

    static Query query() {
        return new QueryImpl();
    }

    static Query page(Integer page, Integer size) {
        return new QueryImpl().page(page, size);
    }

    Sort getSort();

    Limit getLimit();


}
