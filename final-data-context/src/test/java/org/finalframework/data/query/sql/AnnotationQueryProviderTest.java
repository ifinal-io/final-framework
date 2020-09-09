

package org.finalframework.data.query.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.finalframework.data.query.QueryEntity;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 17:52:02
 * @since 1.0
 */
@Slf4j
class AnnotationQueryProviderTest {

    @Test
    void apply() {


        final AnnotationQueryProvider annotationQueryProvider = AnnotationQueryProvider.INSTANCE;
        final String query = annotationQueryProvider.provide("query", QueryEntity.class, MyQuery.class);
        final String value = String.join("", "<script>", query, "</script>");
        final XPathParser parser = new XPathParser(value);
        final XNode script = parser.evalNode("//script");
        final String sql = script.toString();
        logger.info("query ==> \n{}", sql);
        if (logger.isDebugEnabled()) {
            final String[] sqls = sql.split("\n");
            for (String item : sqls) {
                logger.debug(item);
            }
        }
    }
}