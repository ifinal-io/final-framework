package org.finalframework.mybatis.sql.script;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 22:14:45
 * @see org.finalframework.data.query.Query
 * @see OrderSqlNodeBuilder
 * @see LimitSqlNodeBuilder
 * @since 1.0
 */
public class QuerySqlNodeBuilder implements ScriptSqlNodeBuilder {

    private final OrderSqlNodeBuilder orderSqlNodeBuilder = new OrderSqlNodeBuilder();
    private final LimitSqlNodeBuilder limitSqlNodeBuilder = new LimitSqlNodeBuilder();

    @Override
    public void build(Node script, String value) {
        final Document document = script.getOwnerDocument();

        final Element ifQueryNotNull = document.createElement("if");
        ifQueryNotNull.setAttribute("test", String.format("%s != null", value));

        orderSqlNodeBuilder.build(ifQueryNotNull, String.format("%s.sort", value));
        limitSqlNodeBuilder.build(ifQueryNotNull, String.format("%s.limit", value));

        script.appendChild(ifQueryNotNull);
    }
}

