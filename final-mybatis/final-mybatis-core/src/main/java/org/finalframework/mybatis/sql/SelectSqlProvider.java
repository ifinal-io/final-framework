package org.finalframework.mybatis.sql;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.finalframework.data.query.QEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @since 1.0
 */
public class SelectSqlProvider implements AbsMapperSqlProvider, ProviderMethodResolver {

    public String select(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        final Element select = helper.trim().prefix("SELECT").build();

        final Element foreach = helper.foreach().collection("properties").item("property").separator(",").build();


        final Element ifHasView = document.createElement("if");
        ifHasView.setAttribute("test", "property.hasView(view)");
        ifHasView.appendChild(cdata(document, "${property.column}"));
        foreach.appendChild(ifHasView);

//        foreach.appendChild();
        select.appendChild(foreach);

        script.appendChild(select);
        final Node from = trim(document, "FROM", null);
        from.appendChild(table(document, null));
        script.appendChild(from);


        script.appendChild(where(document, whenIdsNotNull(document)));

    }
}

