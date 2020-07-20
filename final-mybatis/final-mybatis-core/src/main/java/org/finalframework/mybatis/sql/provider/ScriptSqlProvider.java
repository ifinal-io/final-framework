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


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-03 21:42:15
 * @see ProviderSqlSource
 * @since 1.0
 */
public interface ScriptSqlProvider extends SqlProvider {

    @Override
    default String provide(ProviderContext context, Map<String, Object> parameters) {
        final Logger logger = LoggerFactory.getLogger(context.getMapperType() + "." + context.getMapperMethod().getName());
        final StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        doProvide(sql, context, parameters);
        sql.append("</script>");
        logger.info("sql ==> {}", sql.toString());
        return sql.toString();
    }

    void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters);


}

