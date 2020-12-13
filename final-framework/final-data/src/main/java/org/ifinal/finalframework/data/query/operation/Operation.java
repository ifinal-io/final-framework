package org.ifinal.finalframework.data.query.operation;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Operation {

    /**
     * return the name of {@link Operation}.
     *
     * @return operation name
     */
    @NonNull
    String name();

    /**
     * 比较运算符
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    enum CompareOperation implements Operation {
        NULL, NOT_NULL,
        EQUAL, NOT_EQUAL,
        GREAT_THAN, GREAT_THAN_EQUAL,
        LESS_THAN, LESS_THAN_EQUAL,
        IN, NOT_IN,
        LIKE, NOT_LIKE,
        BETWEEN, NOT_BETWEEN
    }

}
