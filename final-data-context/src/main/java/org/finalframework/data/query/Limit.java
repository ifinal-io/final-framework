package org.finalframework.data.query;

import org.springframework.lang.Nullable;

/**
 * The sql node of limit which would be append to sql like this.
 *
 * <pre class="code">
 *      <trim prefix="LIMIT">
 *          <if test="limit.offset != null">
 *              #{limit.offset},
 *          </if>
 *          <if test="limit.limit != null">
 *              #{limit.limit}
 *          </if>
 *      </trim>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-12 17:11:43
 * @since 1.0
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
     * @param sql        sql
     * @param expression expression
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
