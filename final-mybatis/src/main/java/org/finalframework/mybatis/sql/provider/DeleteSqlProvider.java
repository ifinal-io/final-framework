package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.data.query.Query;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *     <code>
 *         <script>
 *             <trim prefix="DELETE FROM">
 *                  ${table}
 *             </trim>
 *             <where>
 *                 <trim>
 *                     ${properties.idProperty.column}
 *                 </trim>
 *                 <foreach collection="ids" item="id" open=" IN (" close=")" separator=",">#{id}</foreach>
 *             </where>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#delete(String, Collection, Query)
 * @since 1.0
 */
public class DeleteSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    @SuppressWarnings("unused")
    public String delete(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        Object ids = parameters.get("ids");
        Object query = parameters.get("query");

        sql.append("<trim prefix=\"DELETE FROM\">").append("${table}").append("</trim>");

        if (ids != null) {
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            ((Query) query).apply(sql, "query");
        }


    }
}

