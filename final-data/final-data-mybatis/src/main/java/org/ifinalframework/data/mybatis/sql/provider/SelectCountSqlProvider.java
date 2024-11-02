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

import java.util.Collection;
import java.util.Map;

/**
 * @author iimik
 * @version 1.0.0
 * @see AbsMapper#selectCount(String, Collection, IQuery)
 * @since 1.0.0
 */

public class SelectCountSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String QUERY = "query";

    @SuppressWarnings("unused")
    public String selectCount(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    public void doProvide(final StringBuilder sql, final ProviderContext context,
                          final Map<String, Object> parameters) {

        Object ids = parameters.get("ids");
        Object query = parameters.get(QUERY);

        final Class<?> entity = getEntityClass(context.getMapperType());

        sql.append("<trim prefix=\"SELECT COUNT(*) FROM\">${table}</trim>");

        if (ids != null) {
            sql.append(whereIdsNotNull());
        } else {
            appendQuery(sql, entity, query);
            appendOrders(sql);
            appendGroups(sql);
            appendLimit(sql);
        }

    }

}

