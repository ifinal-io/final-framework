/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query;

import java.util.Collections;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import org.ifinalframework.velocity.Velocities;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CriterionExpressionTest.
 *
 * @author iimik
 * @version 1.2.1
 * @since 1.2.1
 */
@Slf4j
class CriterionExpressionTest {

    @Test
    void isNull() {
        final Criterion criterion = CriterionTarget.from("name").isNull();
        Assertions.assertEquals("<![CDATA[ AND name IS NULL ]]>", Velocities.getValue(CriterionExpression.IS_NULL, criterion));
    }

    @Test
    void isNotNull() {
        final Criterion criterion = CriterionTarget.from("name").isNotNull();
        Assertions.assertEquals("<![CDATA[ AND name IS NOT NULL ]]>",
                Velocities.getValue(CriterionExpression.IS_NOT_NULL, criterion));
    }

    @Test
    void eq() throws OgnlException {
        final Criterion criterion = CriterionTarget.from("name").eq(null);
        appendValue(criterion);
        final String script = Velocities.getValue(CriterionExpression.EQUAL, criterion);
        assertEquals("<if test=\"value != null\"><![CDATA[ AND name = #{value} ]]></if>",
                script);

        final Configuration configuration = new Configuration();
        final SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(configuration,
                String.join("", "<script>", script, "</script>"), Map.class);

        final Map<String, String> map = Collections.singletonMap("value", "haha");
        final BoundSql boundSql = sqlSource.getBoundSql(map);
        String sql = boundSql.getSql();
        logger.info("sql={}", sql);
        final MetaObject metaObject = configuration.newMetaObject(map);
        for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
            logger.info("{}={}", parameterMapping.getProperty(), metaObject.getValue(parameterMapping.getProperty()));
            sql = sql.replaceFirst("\\?", String.valueOf(metaObject.getValue(parameterMapping.getProperty())));
        }
        logger.info("sql={}", sql);

    }

    @Test
    void neq() {
        final Criterion criterion = CriterionTarget.from("name").neq(null);
        appendValue(criterion);
        Assertions.assertEquals("<if test=\"value != null\"><![CDATA[ AND name != #{value} ]]></if>",
                Velocities.getValue(CriterionExpression.NOT_EQUAL, criterion));
    }

    @Test
    void gt() {
        final Criterion criterion = CriterionTarget.from("name").gt(null);
        appendValue(criterion);
        Assertions.assertEquals("<if test=\"value != null\"><![CDATA[ AND name > #{value} ]]></if>",
                Velocities.getValue(CriterionExpression.GREAT_THAN, criterion));
    }

    @Test
    void gte() {
        final Criterion criterion = CriterionTarget.from("name").gte(null);
        appendValue(criterion);
        Assertions.assertEquals("<if test=\"value != null\"><![CDATA[ AND name >= #{value} ]]></if>",
                Velocities.getValue(CriterionExpression.GREAT_THAN_EQUAL, criterion));
    }

    @Test
    void lt() {
        final Criterion criterion = CriterionTarget.from("name").lt(null);
        appendValue(criterion);
        Assertions.assertEquals("<if test=\"value != null\"><![CDATA[ AND name < #{value} ]]></if>",
                Velocities.getValue(CriterionExpression.LESS_THAN, criterion));
    }

    @Test
    void lte() {
        final Criterion criterion = CriterionTarget.from("name").lte(null);
        appendValue(criterion);
        Assertions.assertEquals("<if test=\"value != null\"><![CDATA[ AND name <= #{value} ]]></if>",
                Velocities.getValue(CriterionExpression.LESS_THAN_EQUAL, criterion));
    }

    @Test
    void between() {
        final Criterion criterion = CriterionTarget.from("name").between(null, null);
        appendValue(criterion);
        Assertions.assertEquals(
                "<if test=\"value != null and value.min != null and value.max != null\"><![CDATA[ AND name BETWEEN #{value.min} AND #{value.max} ]]></if>",
                Velocities.getValue(CriterionExpression.BETWEEN, criterion));
    }

    @Test
    void notBetween() {
        final Criterion criterion = CriterionTarget.from("name").between(null, null);
        appendValue(criterion);
        Assertions.assertEquals(
                "<if test=\"value != null and value.min != null and value.max != null\"><![CDATA[ AND name NOT BETWEEN #{value.min} AND #{value.max} ]]></if>",
                Velocities.getValue(CriterionExpression.NOT_BETWEEN, criterion));
    }

    private void appendValue(Criterion criterion) {
        ((CriterionAttributes) criterion).put("value", "value");
    }


}
