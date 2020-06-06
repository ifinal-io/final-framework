package org.finalframework.data.query;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
     * @param parent
     * @param expression
     */
    @Override
    default void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();
        final Element limit = document.createElement("trim");
        limit.setAttribute("prefix", "LIMIT");

        final Element ifOffsetNotNull = document.createElement("if");
        ifOffsetNotNull.setAttribute("test", String.format("%s.offset != null", expression));
        ifOffsetNotNull.appendChild(document.createTextNode(String.format("#{%s.offset},", expression)));
        limit.appendChild(ifOffsetNotNull);

        final Element ifLimitNotNull = document.createElement("if");
        ifLimitNotNull.setAttribute("test", String.format("%s.limit != null", expression));
        ifLimitNotNull.appendChild(document.createTextNode(String.format("#{%s.limit}", expression)));
        limit.appendChild(ifLimitNotNull);


        parent.appendChild(limit);
    }
}
