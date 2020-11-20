package org.finalframework.mybatis.sql.provider;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.annotation.data.AutoInc;
import org.finalframework.annotation.data.PrimaryKey;
import org.finalframework.annotation.query.*;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/18 20:55:01
 * @since 1.0
 */
@Slf4j
class SqlProviderHelperTest {


    @Test
    void and() {
        AndQuery query = new AndQuery();
        query.setA("a");
//        query.setB(new BetweenValue<>("minB", "maxB"));
//        query.setC(Arrays.asList("c1", "c2", "c3"));
        query.setAa("aa");
    }

    @Test
    void or() {
        OrQuery query = new OrQuery();
        query.setA("a");
        query.setB(new BetweenValue<>("minB", "maxB"));
        query.setC(Arrays.asList("c1", "c2", "c3"));
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
    }

    @Test
    void andOr() {
        AndOrQuery query = new AndOrQuery();
        query.setA("a");
        InnerQuery innerQuery = new InnerQuery();
        innerQuery.setB("b");
        innerQuery.setC("c");
        query.setInnerQuery(innerQuery);
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
    }

    @Test
    void orAnd() {
        OrAndQuery query = new OrAndQuery();
        query.setA("a");
        InnerQuery innerQuery = new InnerQuery();
        innerQuery.setB("b");
        innerQuery.setC("c");
        query.setInnerQuery(innerQuery);
        logger.info(SqlProviderHelper.query(Bean.class, query).getScript());
        logger.info(SqlProviderHelper.query(Bean.class, query).getSql());
    }

    static interface BeanMapper extends AbsMapper<Long, Bean> {

    }

    @Data
    static class Bean implements IEntity<Long> {
        @AutoInc
        @PrimaryKey
        private Long id;
        private String a;
        private String b;
        private String c;
    }

    @Data
    static class AndQuery implements IQuery {
        @Equal
        private String a;
        //        @NotBetween
//        private BetweenValue<String> b;
//        @NotIn
//        private List<String> c;
        @JsonContains(path = "$.a", property = "a")
        private String aa;


    }

    @Data
    @Or
    static class OrQuery implements IQuery {
        @Equal
        private String a;
        @NotBetween
        private BetweenValue<String> b;
        @NotIn
        private List<String> c;
    }

    @Data
    static class AndOrQuery implements IQuery {
        @Equal
        private String a;
        @Or
        private InnerQuery innerQuery;
    }

    @Data
    @Or
    static class OrAndQuery implements IQuery {
        @Equal
        private String a;
        @Criteria
        private InnerQuery innerQuery;

    }

    @Data
    static class InnerQuery {
        @Equal
        private String b;
        @NotEqual
        private String c;
    }
}