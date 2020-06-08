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
 * @see org.finalframework.mybatis.mapper.AbsMapper#selectCount(String, Collection, Query)
 * @since 1.0
 */
public class SelectCountSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    public String selectCount(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        Object ids = parameters.get("ids");
        Object query = parameters.get("query");

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        final Element select = helper.trim().prefix("SELECT COUNT(*) FROM").build();
        select.appendChild(helper.cdata("${table}"));
        script.appendChild(select);

        if (ids != null) {
            script.appendChild(where(document, whereIdsNotNull(document)));
        } else if (query instanceof Query) {
            ((Query) query).apply(script, "query");
        }


    }
}

