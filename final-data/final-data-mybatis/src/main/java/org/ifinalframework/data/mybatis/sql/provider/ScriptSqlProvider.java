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

import org.springframework.lang.NonNull;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;

import java.util.Map;

/**
 * @author iimik
 * @version 1.0.0
 * @see ProviderSqlSource
 * @since 1.0.0
 */
public interface ScriptSqlProvider extends SqlProvider {

    @NonNull
    @Override
    default String provide(@NonNull ProviderContext context, @NonNull Map<String, Object> parameters) {
        parameters.put("mapperType", context.getMapperType());
        parameters.put("mapperMethod", context.getMapperMethod());
        parameters.putIfAbsent("orders", null);
        parameters.putIfAbsent("groups", null);
        parameters.putIfAbsent("limit", null);


        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        doProvide(sql, context, parameters);
        sql.append("</script>");
        return sql.toString();
    }

    void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters);

}

