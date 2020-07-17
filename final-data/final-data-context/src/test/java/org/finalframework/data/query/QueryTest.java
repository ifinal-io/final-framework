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

package org.finalframework.data.query;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.finalframework.data.query.criterion.CriterionValue;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.finalframework.data.query.operation.DateOperation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-04 16:54:32
 * @since 1.0
 */
class QueryTest {
    private static final Logger logger = LoggerFactory.getLogger(QueryTest.class);

    @Test
    void apply() {
        final XPathParser parser = new XPathParser("<script></script>");
        final XNode script = parser.evalNode("//script");

        final QEntity<?, ?> entity = QEntity.from(QueryEntity.class);
        QProperty<String> name = entity.getProperty("name");
        QProperty<Integer> age = entity.getProperty("age");
        QProperty<Integer> intList = entity.getProperty("intList");

        final Query query = new Query();
        query.where(
                name.eq("haha"),
                name.apply(value -> new SimpleCriterionFunction(value, DateOperation.DATE)).eq(2),
                name.isNull(),
                name.isNotNull(),
                name.jsonContains(1, "$.a"),
                age.gt(CriterionValue.from(12)),
                Criteria.or(name.eq("haha"),
                        age.gt(12)));
        query.sort(name.asc(), name.desc());

        query.limit(20L, 100L);

        query.apply(script.getNode(), "query");

        final String sql = script.toString();
        if (logger.isDebugEnabled()) {
            final String[] sqls = sql.split("\n");
            for (String item : sqls) {
                logger.debug(item);
            }
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("query", query);

        final XMLLanguageDriver languageDriver = new XMLLanguageDriver();
        final SqlSource sqlSource = languageDriver.createSqlSource(new Configuration(), script, null);
        final BoundSql boundSql = sqlSource.getBoundSql(parameter);
        logger.info("Sql: ==> {}", boundSql.getSql());
        for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
            logger.info("Parameter ==> {}={}", parameterMapping.getProperty(), OgnlCache.getValue(parameterMapping.getProperty(), parameter));
        }


    }
}