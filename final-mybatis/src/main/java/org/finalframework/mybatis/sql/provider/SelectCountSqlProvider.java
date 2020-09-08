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

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *     <code>
 *         <trim prefix="SELECT COUNT(*) FROM ">
 *              ${table}
 *         </trim>
 *         <where>
 *             id <foreach collection="ids" item="id" open=" IN (" separator="," close=")">#{id}</foreach>
 *         </where>
 *         ${query}
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#selectCount(String, Collection, Query)
 * @since 1.0
 */

public class SelectCountSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    @SuppressWarnings("unused")
    public String selectCount(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {


        Object ids = parameters.get("ids");
        Object query = parameters.get("query");

        sql.append("<trim prefix=\"SELECT COUNT(*) FROM\">${table}</trim>");


        if (ids != null) {
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            ((Query) query).apply(sql, "query");
        }


    }

}
