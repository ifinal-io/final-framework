package org.ifinal.finalframework.mybatis.sql.script;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LimitSqlNodeBuilder implements ScriptSqlNodeBuilder {
    @Override
    public void build(Node script, String value) {
        final Document document = script.getOwnerDocument();
        final Element ifNotNull = document.createElement("if");

        ifNotNull.setAttribute("test", String.format("%s != null", value));

        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "LIMIT");

        final Element ifOffsetNotNull = document.createElement("if");
        ifOffsetNotNull.setAttribute("test", String.format("%s.offset != null", value));
        ifOffsetNotNull.appendChild(document.createTextNode(String.format("#{%s.offset},", value)));
        trim.appendChild(ifOffsetNotNull);


        final Element ifLimitNotNull = document.createElement("if");
        ifLimitNotNull.setAttribute("test", String.format("%s.limit != null", value));
        ifLimitNotNull.appendChild(document.createTextNode(String.format("#{%s.limit}", value)));
        trim.appendChild(ifLimitNotNull);


        ifNotNull.appendChild(trim);


        script.appendChild(ifNotNull);
    }
}

