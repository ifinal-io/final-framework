package org.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 17:11:43
 * @since 1.0
 */
public interface Limit extends SqlNode {

    static Limit limit(Long offset, Long limit) {
        return new LimitImpl(offset, limit);
    }

    static Limit limit(Long limit) {
        return limit(null, limit);
    }


    Long getOffset();

    Long getLimit();

    /**
     * <pre>
     *     <code>
     *         <trim prefix="LIMIT">
     *              <if test="limit.offset != null">
     *                  #{limit.offset},
     *              </if>
     *              #{limit.limit}
     *         </trim>
     *     </code>
     * </pre>
     *
     * @param sql
     * @param expression
     */
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
