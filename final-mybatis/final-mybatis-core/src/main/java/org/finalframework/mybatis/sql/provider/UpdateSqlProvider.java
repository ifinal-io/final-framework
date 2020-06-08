package org.finalframework.mybatis.sql.provider;


import java.util.Collection;
import java.util.Map;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <pre>
 *     <code>
 *         <script>
 *             <trim prefix="UPDATE">
 *                  ${table}
 *             </trim>
 *             <set>
 *
 *             </set>
 *             <where>
 *
 *             </where>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, Query)
 * @since 1.0
 */
public class UpdateSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection,
     * Query)
     */
    public String update(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        final Object ids = parameters.get("ids");
        final Object query = parameters.get("query");

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        final Element update = helper.trim().prefix("UPDATE").build();
        update.appendChild(helper.cdata("${table}"));
        script.appendChild(update);

        Element set = document.createElement("set");

        if (parameters.containsKey("entity") && parameters.get("entity") != null) {

        } else if (parameters.containsKey("update") && parameters.get("update") != null) {

        }

        script.appendChild(set);

        if (ids != null) {
            script.appendChild(where(document, whereIdsNotNull(document)));
        } else if (query instanceof Query) {
            ((Query) query).apply(script, "query");
        }


    }
}

