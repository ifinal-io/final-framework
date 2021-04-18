package org.ifinal.finalframework.data.query.sql;

import org.ifinal.finalframework.query.QueryProvider;

/**
 * AbsQueryProvider.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsQueryProvider implements QueryProvider {

    private static final String LIMIT = "<trim prefix=\"LIMIT \">"
        + "     <if test=\"query.offset != null\">"
        + "         #{query.offset},"
        + "     </if>"
        + "     <if test=\"query.limit != null\">"
        + "         #{query.limit}"
        + "     </if>"
        + "</trim>";

    private static final String ORDERS = "<foreach collection=\"query.orders\" item=\"item\" open=\"ORDER BY\" separator=\",\">${item}</foreach>";

    private static final String GROUPS = "<foreach collection=\"query.groups\" item=\"item\" open=\"GROUP BY\" separator=\",\">${item}</foreach>";

    @Override
    public String groups() {
        return GROUPS;
    }

    @Override
    public String orders() {
        return ORDERS;
    }

    @Override
    public String limit() {
        return LIMIT;
    }

}
