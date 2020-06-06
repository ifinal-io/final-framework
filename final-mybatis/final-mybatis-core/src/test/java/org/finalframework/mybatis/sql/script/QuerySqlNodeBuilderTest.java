package org.finalframework.mybatis.sql.script;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 22:32:18
 * @since 1.0
 */
class QuerySqlNodeBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(LimitSqlNodeBuilderTest.class);

    @Test
    void build() {
        final QuerySqlNodeBuilder builder = new QuerySqlNodeBuilder();
        final XPathParser parser = new XPathParser("<script></script>");
        final XNode script = parser.evalNode("//script");
        builder.build(script.getNode(), "query");
        final String sql = script.toString();
        if (logger.isDebugEnabled()) {
            final String[] sqls = sql.split("\n");
            for (String item : sqls) {
                logger.debug(item);
            }
        }
    }
}