package org.ifinal.finalframework.mybatis.pagehelper.parser;

import com.github.pagehelper.parser.OrderByParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * OrderByParserTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class OrderByParserTest {

    @Test
    void converToOrderBySql() {
        final String sql = "SELECT * FROM table ORDER BY name DESC";
        String orderBySql = OrderByParser.converToOrderBySql(sql, null);
        logger.info(sql);
        logger.info(orderBySql);
    }

}
