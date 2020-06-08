package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.data.query.Query;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#select(String, Class, Collection, Query)
 * @since 1.0
 */
public class SelectSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    public String select(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        Object ids = parameters.get("ids");
        Object query = parameters.get("query");

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        final Element select = helper.trim().prefix("SELECT").build();

        final Element foreach = helper.foreach().collection("properties").item("property").separator(",").build();

        final Element ifHasView = document.createElement("if");
        ifHasView.setAttribute("test", "property.hasView(view)");
        ifHasView.appendChild(helper.cdata("${property.column}"));
        foreach.appendChild(ifHasView);

//        foreach.appendChild();
        select.appendChild(foreach);

        script.appendChild(select);
        final Node from = helper.trim().prefix("FROM").build();
        from.appendChild(helper.cdata("${table}"));
        script.appendChild(from);

        if (ids != null) {
            script.appendChild(where(document, whereIdsNotNull(document)));
        } else if (query instanceof Query) {
            ((Query) query).apply(script, "query");
        }


    }
}

