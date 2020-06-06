package org.finalframework.mybatis.sql;


import org.finalframework.data.query.QEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-03 21:58:33
 * @since 1.0
 */
public interface AbsMapperSqlProvider extends ScriptSqlProvider {

    default Node trim(Document document, String prefix, String suffix) {
        final Element trim = document.createElement("trim");
        if (prefix != null) {
            trim.setAttribute("prefix", prefix);
        }
        if (suffix != null) {
            trim.setAttribute("suffix", suffix);
        }
        return trim;
    }

    default Node table(Document document, QEntity<?, ?> entity) {
        return cdata(document, "${table}");
    }

    default Node cdata(Document document, String data) {
        return document.createCDATASection(data);
    }

    default Node text(Document document, String data) {
        return document.createTextNode(data);
    }

    default Node where(Document document, Node... whens) {
        final Element where = document.createElement("where");

        final Element choose = document.createElement("choose");
        for (Node when : whens) {
            choose.appendChild(when);
        }
        where.appendChild(choose);

        return where;
    }

    default Node whenIdsNotNull(Document document) {
        final Element when = document.createElement("when");
        when.setAttribute("test", "ids != null");
        when.appendChild(text(document, "${properties.idProperty.column}"));
        final Element ifIdPropertyNotNull = document.createElement("if");
        ifIdPropertyNotNull.setAttribute("test", "properties.idProperty != null");
        ifIdPropertyNotNull.appendChild(text(document, "${properties.idProperty.column}"));
        when.appendChild(ifIdPropertyNotNull);


        final Node in = trim(document, "IN", null);
//        in.appendChild(text(document, "${properties.idProperty.column}"));

        final Element foreach = document.createElement("foreach");

        foreach.setAttribute("collection", "ids");
        foreach.setAttribute("item", "id");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("close", ")");
        foreach.appendChild(cdata(document, "#{id}"));

        in.appendChild(foreach);

        when.appendChild(in);

        return when;
    }


}

