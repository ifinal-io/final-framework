package org.ifinal.finalframework.data.query.sql;

import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.QueryEntity;
import org.ifinal.finalframework.query.Criteria;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

/**
 * DefaultQueryProviderTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class DefaultQueryProviderTest {

    @Test
    void where(){
        final QEntity<?, ?> entity = QEntity.from(QueryEntity.class);
        QProperty<String> name = entity.getProperty("name");
        QProperty<Integer> age = entity.getProperty("age");

        Query query = new Query().where(
            name.eq("haha"),
            age.and(2).gt(12),
            Criteria.or(name.neq("ffa"),age.neq(12))
        );

        DefaultQueryProvider provider = new DefaultQueryProvider(query);

        String where = provider.where();
        logger.info("where={}", where);

        final XPathParser parser = new XPathParser(String.join("", "<script>", where, "</script>"));
        final XNode script = parser.evalNode("//script");

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
        logger.info("Sql: ==> {}", boundSql.getSql());

    }

}
