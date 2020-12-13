package org.ifinal.finalframework.mybatis.sql.provider;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.annotation.data.AutoInc;
import org.ifinal.finalframework.annotation.data.PrimaryKey;
import org.ifinal.finalframework.annotation.query.BetweenValue;
import org.ifinal.finalframework.annotation.query.Criteria;
import org.ifinal.finalframework.annotation.query.Equal;
import org.ifinal.finalframework.annotation.query.JsonContains;
import org.ifinal.finalframework.annotation.query.NotBetween;
import org.ifinal.finalframework.annotation.query.NotEqual;
import org.ifinal.finalframework.annotation.query.NotIn;
import org.ifinal.finalframework.annotation.query.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class SqlProviderHelperTest {

    @Test
    void and() {
        AndQuery query = new AndQuery();
        query.setColumnA("a");
        query.setColumnA2("aa");
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
        Assertions.assertNotNull(SqlProviderHelper.query(Bean.class, query).getSql());
    }

    @Test
    void or() {
        OrQuery query = new OrQuery();
        query.setColumnA("a");
        query.setColumnB(new BetweenValue<>("minB", "maxB"));
        query.setColumnC(Arrays.asList("c1", "c2", "c3"));
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
        Assertions.assertNotNull(SqlProviderHelper.query(Bean.class, query).getSql());
    }

    @Test
    void andOr() {
        AndOrQuery query = new AndOrQuery();
        query.setColumnA("a");
        InnerQuery innerQuery = new InnerQuery();
        innerQuery.setColumnB("b");
        innerQuery.setColumnC("c");
        query.setInnerQuery(innerQuery);
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
        Assertions.assertNotNull(SqlProviderHelper.query(Bean.class, query).getSql());

    }

    @Test
    void orAnd() {
        OrAndQuery query = new OrAndQuery();
        query.setColumnA("a");
        InnerQuery innerQuery = new InnerQuery();
        innerQuery.setColumnB("b");
        innerQuery.setColumnC("c");
        query.setInnerQuery(innerQuery);
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
        Assertions.assertNotNull(SqlProviderHelper.query(Bean.class, query).getSql());
    }

    @Data
    static class Bean implements IEntity<Long> {

        @AutoInc
        @PrimaryKey
        private Long id;

        private String columnA;

        private String columnB;

        private String columnC;

    }

    @Data
    static class AndQuery implements IQuery {

        @Equal
        private String columnA;

        @JsonContains(path = "$.columnA", property = "columnA")
        private String columnA2;

    }

    @Data
    @Or
    static class OrQuery implements IQuery {

        @Equal
        private String columnA;

        @NotBetween
        private BetweenValue<String> columnB;

        @NotIn
        private List<String> columnC;

    }

    @Data
    static class AndOrQuery implements IQuery {

        @Equal
        private String columnA;

        @Or
        private InnerQuery innerQuery;

    }

    @Data
    @Or
    static class OrAndQuery implements IQuery {

        @Equal
        private String columnA;

        @Criteria
        private InnerQuery innerQuery;

    }

    @Data
    static class InnerQuery {

        @Equal
        private String columnB;

        @NotEqual
        private String columnC;

    }

}