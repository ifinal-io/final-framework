/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
 *                 <foreach collection="ids" item="id" open="IN(" close=")" separator=",">#{id}</foreach>
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

    public String delete(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        Object ids = parameters.get("ids");
        Object query = parameters.get("query");

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        final Element delete = helper.trim().prefix("DELETE FROM").build();
        delete.appendChild(helper.cdata("${table}"));

        script.appendChild(delete);

        if (ids != null) {
            script.appendChild(where(document, whereIdsNotNull(document)));
        } else if (query instanceof Query) {
            ((Query) query).apply(script, "query");
        }


    }
}

