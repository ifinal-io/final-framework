package org.finalframework.data.query;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-04 16:45:10
 * @since 1.0
 */
class LimitTest {

    private static final Logger logger = LoggerFactory.getLogger(LimitTest.class);

    @Test
    void apply() {

        final XPathParser parser = new XPathParser("<script></script>");
        final XNode script = parser.evalNode("//script");

        Limit.limit(20L, 20L).apply(script.getNode(), "query.limit");

        logger.info(script.toString());

    }
}