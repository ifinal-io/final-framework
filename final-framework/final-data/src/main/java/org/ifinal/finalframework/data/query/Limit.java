package org.ifinal.finalframework.data.query;

import org.springframework.lang.Nullable;

/**
 * The sql node of limit which would be append to sql like this.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Limit extends SqlNode {

    /**
     * return a {@link Limit} with {@code offset} and {@code limit}.
     *
     * @param offset offset
     * @param limit  limit
     * @return a limit
     */
    static Limit limit(@Nullable Long offset, @Nullable Long limit) {
        return new LimitImpl(offset, limit);
    }

    /**
     * return a {@link Limit} with {@code limit}.
     *
     * @param limit limit
     * @return limit
     */
    static Limit limit(@Nullable Long limit) {
        return limit(null, limit);
    }


    /**
     * return limit offset
     *
     * @return limit offset
     */
    @Nullable
    Long getOffset();

    /**
     * return limit limit
     *
     * @return limit limit
     */
    @Nullable
    Long getLimit();

    @Override
    default void apply(StringBuilder sql, String expression) {

        sql.append("<trim prefix=\"LIMIT\">")
                .append(String.format("<if test=\"%s.offset != null\">", expression))
                .append(String.format("#{%s.offset},", expression))
                .append("</if>")
                .append(String.format("<if test=\"%s.limit != null\">", expression))
                .append(String.format("#{%s.limit}", expression))
                .append("</if>")
                .append("</trim>");

    }
}
