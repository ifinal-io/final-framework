package org.ifinal.finalframework.data.query;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.ifinal.finalframework.data.query.criterion.CriterionValue;
import org.ifinal.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.ifinal.finalframework.data.query.operation.DateOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class QueryTest {

    private static final Logger logger = LoggerFactory.getLogger(QueryTest.class);

    @Test
    void apply() {

        final StringBuilder builder = new StringBuilder();

        final QEntity<?, ?> entity = QEntity.from(QueryEntity.class);
        QProperty<String> name = entity.getProperty("name");
        QProperty<Integer> age = entity.getProperty("age");

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

        query.apply(builder, "query");

        final XPathParser parser = new XPathParser(String.join("", "<script>", builder.toString(), "</script>"));
        final XNode script = parser.evalNode("//script");
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
            logger.info("Parameter ==> {}={}", parameterMapping.getProperty(),
                OgnlCache.getValue(parameterMapping.getProperty(), parameter));
        }

        Assertions.assertNotNull(boundSql.getSql());

    }

}
