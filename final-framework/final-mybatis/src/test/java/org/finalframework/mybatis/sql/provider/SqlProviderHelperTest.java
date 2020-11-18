package org.finalframework.mybatis.sql.provider;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.annotation.data.AutoInc;
import org.finalframework.annotation.data.PrimaryKey;
import org.finalframework.annotation.query.BetweenValue;
import org.finalframework.annotation.query.Equal;
import org.finalframework.annotation.query.NotBetween;
import org.finalframework.annotation.query.NotIn;
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
        query.setB(new BetweenValue<>("minB", "maxB"));
        query.setC(Arrays.asList("c1", "c2", "c3"));
        logger.info(SqlProviderHelper.query(Bean.class, query.getClass()));
        logger.info(SqlProviderHelper.query(Bean.class, query));
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
        @NotBetween
        private BetweenValue<String> b;
        @NotIn
        private List<String> c;

    }
}