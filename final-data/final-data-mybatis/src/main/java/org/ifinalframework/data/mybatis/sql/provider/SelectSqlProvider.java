/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.mybatis.sql.provider;

import org.ifinalframework.core.IQuery;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.data.mybatis.sql.AbsMapperSqlProvider;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author iimik
 * @version 1.0.0
 * @see AbsMapper#select(String, Class, Collection, IQuery)
 * @see AbsMapper#selectOne(String, Class, Serializable, IQuery)
 * @see AbsMapper#selectIds(Map)
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class SelectSqlProvider implements AbsMapperSqlProvider {

    public static final String QUERY_PARAMETER_NAME = "query";

    private static final String SELECT_METHOD_NAME = "select";

    private static final String SELECT_ONE_METHOD_NAME = "selectOne";

    public String select(final ProviderContext context, final Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    public String selectOne(final ProviderContext context, final Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    public String selectIds(final ProviderContext context, final Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    @Override
    public void doProvide(final StringBuilder sql, final ProviderContext context, final Map<String, Object> parameters) {

        final Class<?> entity = getEntityClass(context.getMapperType());
        final String mapperMethodName = context.getMapperMethod().getName();

        sql.append("<trim prefix=\"SELECT\" suffixOverrides=\",\">");
        sql.append("<foreach item=\"column\" collection=\"columns\" separator=\",\">${column}</foreach>");
        sql.append("</trim>");
        sql.append("<trim prefix=\"FROM\">")
                .append("${table}")
                .append("</trim>");

        Object query = parameters.get(QUERY_PARAMETER_NAME);

        if (SELECT_ONE_METHOD_NAME.equals(mapperMethodName) && parameters.get("id") != null) {
            // <where> id = #{id} </where>
            sql.append(whereIdNotNull());
        } else if (SELECT_METHOD_NAME.equals(mapperMethodName) && parameters.get("ids") != null) {
            sql.append(whereIdsNotNull());
        } else {

            appendQuery(sql, entity, query);

            appendOrders(sql);
            appendGroups(sql);
            if (SELECT_ONE_METHOD_NAME.equals(mapperMethodName)) {
                sql.append(" LIMIT 1");
            } else {
                appendLimit(sql);
            }
        }

    }

}

