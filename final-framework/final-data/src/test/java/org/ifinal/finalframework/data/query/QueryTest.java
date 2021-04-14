package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.data.query.criterion.VelocityCriterionValue;
import org.ifinal.finalframework.data.query.sql.DefaultQueryProvider;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.query.AndOr;
import org.ifinal.finalframework.query.Criteria;
import org.ifinal.finalframework.query.Criterion;
import org.ifinal.finalframework.query.CriterionAttributes;
import org.ifinal.finalframework.query.QEntity;
import org.ifinal.finalframework.query.QProperty;
import org.ifinal.finalframework.query.Query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
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

        final QEntity<?, ?> entity = DefaultQEntityFactory.INSTANCE.create(QueryEntity.class);
        QProperty<String> name = entity.getProperty("name");
        QProperty<Integer> age = entity.getProperty("age");

        final Query query = new Query();
        query.where(
            name.eq("haha"),
            name.isNull(),
            name.isNotNull(),
            name.jsonContains(1, "$.a"),
            Criteria.or(name.eq("haha"),
                age.gt(12)));
        query.sort(name.asc(), name.desc());

        query.limit(20L, 100L);

        final StringBuilder builder = new StringBuilder();

        DefaultQueryProvider provider = new DefaultQueryProvider(query);
        Optional.ofNullable(provider.where()).ifPresent(builder::append);
        Optional.ofNullable(provider.orders()).ifPresent(builder::append);
        Optional.ofNullable(provider.limit()).ifPresent(builder::append);

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

    @Test
    void script() {
        final QEntity<?, ?> entity = DefaultQEntityFactory.INSTANCE.create(QueryEntity.class);
        QProperty<String> name = entity.getProperty("name");
        QProperty<Integer> age = entity.getProperty("age");

        StringBuilder sql = new StringBuilder();

        List<Criterion> criteria = Arrays.asList(
//            name.eq("haha"),
//            age.neq(11),
//            name.gt("12"),
//            age.gte(23),
//            name.lt("21"),
            age.and(12).lte(1223)
        );

        sql.append("<where>");

        for (int i = 0; i < criteria.size(); i++) {

            Criterion criterion = criteria.get(i);

            if (criterion instanceof CriterionAttributes) {
                CriterionAttributes attributes = ((CriterionAttributes) criterion);

                CriterionAttributes target = new CriterionAttributes();
                target.putAll(attributes);
                target.put("criterion", String.format("query.criteria[%d]", i));

                String column = target.getColumn();

                if (column.contains("${") || column.contains("#{")) {
                    column = Velocities.getValue(column, target);
                    target.setColumn(column);
                }

                target.put(CriterionAttributes.ATTRIBUTE_NAME_AND_OR, AndOr.AND);
                target.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format("query.criteria[%d].value", i));

                String value = new VelocityCriterionValue(attributes.getString(CriterionAttributes.ATTRIBUTE_NAME_EXPRESSION))
                    .value(target);
                sql.append(value);
            }

        }

        sql.append("</where>");

        logger.info("sql={}", sql);

        final XPathParser parser = new XPathParser(String.join("", "<script>", sql, "</script>"));
        final XNode script = parser.evalNode("//script");
//        final String sqlscript = script.toString();
//        if (logger.isDebugEnabled()) {
//            final String[] sqls = sqlscript.split("\n");
//            for (String item : sqls) {
//                logger.debug(item);
//            }
//        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("query", new Query().where(criteria));

        final XMLLanguageDriver languageDriver = new XMLLanguageDriver();
        final SqlSource sqlSource = languageDriver.createSqlSource(new Configuration(), script, null);
        final BoundSql boundSql = sqlSource.getBoundSql(parameter);
        logger.info("Sql: ==> {}", boundSql.getSql());
        for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
            logger.info("Parameter ==> {}={}", parameterMapping.getProperty(),
                OgnlCache.getValue(parameterMapping.getProperty(), parameter));
        }

        String boundSqlSql = boundSql.getSql();
        logger.info(boundSqlSql);
        Assertions.assertNotNull(boundSql.getSql());

    }

}
