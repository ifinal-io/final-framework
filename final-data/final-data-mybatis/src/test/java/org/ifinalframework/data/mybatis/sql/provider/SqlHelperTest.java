/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.mybatis.sql.provider;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.annotation.AutoInc;
import org.ifinalframework.data.annotation.PrimaryKey;
import org.ifinalframework.data.annotation.criterion.Criteria;
import org.ifinalframework.data.annotation.criterion.Equal;
import org.ifinalframework.data.annotation.criterion.JsonContains;
import org.ifinalframework.data.annotation.criterion.NotBetween;
import org.ifinalframework.data.annotation.criterion.NotEqual;
import org.ifinalframework.data.annotation.criterion.NotIn;
import org.ifinalframework.data.annotation.criterion.Or;
import org.ifinalframework.data.mybatis.sql.util.SqlHelper;
import org.ifinalframework.data.query.AndOr;
import org.ifinalframework.data.query.BetweenValue;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.Query;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class SqlHelperTest {


    @Test
    void and() {
        AndQuery query = new AndQuery();
        query.setColumnA("a");
        query.setColumnA2("aa");
        //        query.where(CriterionTarget.from("columnA").eq(123));
        logger.info(SqlHelper.query(Bean.class, query));
    }

    @Test
    void or() {
        OrQuery query = new OrQuery();
        query.setColumnA("a");
        query.setColumnB(new BetweenValue<>("minB", "maxB"));
        query.setColumnC(Arrays.asList("c1", "c2", "c3"));
        logger.info(SqlHelper.query(Bean.class, query));
    }

    @Test
    void andOr() {
        AndOrQuery query = new AndOrQuery();
        query.setColumnA("a");
        InnerQuery innerQuery = new InnerQuery();
        innerQuery.setColumnB("b");
        innerQuery.setColumnC("c");
        query.setInnerQuery(innerQuery);
        logger.info(SqlHelper.query(Bean.class, query));

    }

    @Test
    void orAnd() {
        OrAndQuery query = new OrAndQuery();
        query.setColumnA("a");
        InnerQuery innerQuery = new InnerQuery();
        innerQuery.setColumnB("b");
        innerQuery.setColumnC("c");
        query.setInnerQuery(innerQuery);
        logger.info(SqlHelper.query(Bean.class, query));
    }

    @Test
    void sql() {
        final PersonQuery query = new PersonQuery();
        query.setName("haha");
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("query", query);
        parameters.put("tenant", 1);
        logger.info("sql={}", SqlHelper.sql(PersonMapper.class, "select", parameters));
        logger.info("query={}", SqlHelper.query(Person.class, query));
    }

    @Test
    void sql2() {
        final Query query = new Query();
        QEntity<?, ?> entity = new DefaultQEntityFactory().create(Person.class);
        query.where(AndOr.OR,
                Arrays.asList(
                        entity.getRequiredProperty("name").jsonExtract("$.a").neq("2"),
                        entity.getRequiredProperty("name").date().eq("123")

                )
        );
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("query", query);
        logger.info("sql={}", SqlHelper.sql(PersonMapper.class, "select", parameters));
        logger.info("query={}", SqlHelper.query(Person.class, query));
    }

    /**
     * Bean.
     */
    @Data
    public static class Bean implements IEntity<Long> {

        @AutoInc
        @PrimaryKey
        private Long id;

        private String columnA;

        private String columnB;

        private String columnC;

    }

    @Data
    static class AndQuery extends PageQuery {

        @Equal
        private String columnA;

        @JsonContains(path = "$.columnA", property = "columnA")
        private String columnA2;

    }

    @Data
    @Or
    static class OrQuery extends PageQuery {

        @Equal
        private String columnA;

        @NotBetween
        private BetweenValue<String> columnB;

        @NotIn
        private List<String> columnC;

    }

    @Data
    static class AndOrQuery extends PageQuery {

        @Equal
        private String columnA;

        @Or
        private InnerQuery innerQuery;

    }

    @Data
    @Or
    static class OrAndQuery extends PageQuery {

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
