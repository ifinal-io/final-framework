package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.query.AndOr;
import org.ifinal.finalframework.annotation.query.Equal;
import org.ifinal.finalframework.annotation.query.Or;
import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.Property;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class IQueryTest {

    private static final Logger logger = LoggerFactory.getLogger(IQueryTest.class);

    @Test
    void apply() {
        final XPathParser parser = new XPathParser("<script></script>");
        final XNode script = parser.evalNode("//script");
        final Document document = script.getNode().getOwnerDocument();

        final QEntity<?, ?> entity = QEntity.from(QueryEntity.class);

        final QueryEntityQuery query = new QueryEntityQuery();

        final Entity<QueryEntityQuery> properties = Entity.from(QueryEntityQuery.class);
        final Element where = document.createElement("where");

        final AndOr andOr = properties.isAnnotationPresent(Or.class) ? AndOr.OR : AndOr.AND;

        for (Property property : properties) {
            if (property.isAnnotationPresent(Equal.class)) {
                final Element element = document.createElement("if");
                element.setAttribute("test", String.format("%s.%s != null", "query", property.getName()));
                element.appendChild(document.createCDATASection(
                    String
                        .format("%s %s = #{%s.%s}", andOr, entity.getProperty(property.getName()).getColumn(), "query",
                            property.getName())));
                where.appendChild(element);
            }
        }

        script.getNode().appendChild(where);

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
