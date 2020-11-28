package org.ifinal.finalframework.mybatis.sql.script;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class LimitSqlNodeBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(LimitSqlNodeBuilderTest.class);

    @Test
    void build() {
        final LimitSqlNodeBuilder builder = new LimitSqlNodeBuilder();
        final XPathParser parser = new XPathParser("<script></script>");
        final XNode script = parser.evalNode("//script");
        builder.build(script.getNode(), "query.limit");
        final String sql = script.toString();
        if (logger.isDebugEnabled()) {
            final String[] sqls = sql.split("\n");
            for (String item : sqls) {
                logger.debug(item);
            }
        }

        Assertions.assertNotNull(sql);
    }
}