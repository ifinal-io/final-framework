

package org.finalframework.data.query.operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:13:24
 * @since 1.0
 */
public interface Operation {

    String name();

    /**
     * 比较运算符
     *
     * @author likly
     * @version 1.0
     * @date 2020-03-31 20:24:48
     * @since 1.0
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
