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

package org.ifinalframework.data.mybatis.sql;

import org.springframework.core.ResolvableType;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.data.mybatis.sql.provider.ScriptSqlProvider;
import org.ifinalframework.data.query.QueryProvider;
import org.ifinalframework.data.query.sql.DefaultQueryProvider;
import org.ifinalframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author iimik
 * @version 1.0.0
 * @see AbsMapper
 * @since 1.0.0
 */
public interface AbsMapperSqlProvider extends ScriptSqlProvider {

    default Class<?> getEntityClass(final Class<?> mapper) {
        return ResolvableType.forClass(mapper).as(Repository.class).resolveGeneric(1);
    }

    default QueryProvider query(String expression, Class<?> entity, Object query) {
        return new DefaultQueryProvider(expression, (Class<? extends IEntity>) entity, query);
    }

    default void appendOrders(StringBuilder sql) {
        sql.append("<if test=\"orders != null\">")
                .append("     <foreach collection=\"orders\" item=\"item\" open=\"ORDER BY\" separator=\",\">${item}</foreach>")
                .append("</if>");
    }

    default void appendGroups(StringBuilder sql) {
        sql.append("<if test=\"groups != null\">")
                .append("     <foreach collection=\"groups\" item=\"item\" open=\"GROUP BY\" separator=\",\">${item}</foreach>")
                .append("</if>");
    }

    default void appendLimit(StringBuilder sql) {
        sql.append("<trim prefix=\"LIMIT \">")
                .append("     <if test=\"limit != null and limit.offset != null\">")
                .append("         #{limit.offset},")
                .append("     </if>")
                .append("     <if test=\"limit != null and limit.limit != null\">")
                .append("         #{limit.limit}")
                .append("     </if>")
                .append("</trim>");
    }

    default void appendQuery(StringBuilder sql, Class<?> entity, Object query) {

        QueryProvider provider = null;

        if (query instanceof IQuery) {
            provider = query("query", entity, query);
        }

        Optional.ofNullable(provider).ifPresent(it -> {
            Optional.ofNullable(it.where()).ifPresent(sql::append);
        });
    }

    default String whereIdNotNull() {
        return """
                <where>
                    <if test=\"properties.hasTenantProperty() and tenant != null\">
                        ${properties.tenantProperty.column} = #{tenant} AND 
                    </if>
                    ${properties.idProperty.column} = #{id}
                </where>
               """;
    }

    default String whereIdsNotNull() {
        return """
                <where>
                    <if test=\"properties.hasTenantProperty() and tenant != null\">
                        ${properties.tenantProperty.column} = #{tenant} AND 
                    </if>
                    ${properties.idProperty.column}
                    <foreach collection=\"ids\" item=\"id\" open=\" IN (\" separator=\",\" close=\")\">#{id}</foreach>
                </where>
               """;
    }

}

