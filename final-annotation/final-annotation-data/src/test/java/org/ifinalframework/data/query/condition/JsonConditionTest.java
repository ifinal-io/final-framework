/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query.condition;

import java.util.regex.Pattern;

import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.CriterionTarget;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JsonConditionTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
class JsonConditionTest extends ConditionTest {

    @Test
    void jsonContains() {
        final Criterion criterion = CriterionTarget.from("column").jsonContains(1);
        final String sql = sql((CriterionAttributes) criterion);
        logger.info(sql);
        assertTrue(Pattern.matches("AND JSON_CONTAINS\\(\\s*column\\s*,\\s*1\\s*\\)", sql));
    }

    @Test
    void notJsonContains(){
        final Criterion criterion = CriterionTarget.from("column").notJsonContains(1);
        final String sql = sql((CriterionAttributes) criterion);
        logger.info(sql);
        assertTrue(Pattern.matches("AND !JSON_CONTAINS\\(\\s*column\\s*,\\s*1\\s*\\)", sql));
    }

    @Test
    void jsonArrayContains(){
        final Criterion criterion = CriterionTarget.from("column").jsonArrayContains(1);
        final String sql = sql((CriterionAttributes) criterion);
        logger.info(sql);
        assertTrue(Pattern.matches("AND JSON_CONTAINS\\(\\s*column\\s*,\\s*JSON_ARRAY\\(\\s*1\\s*\\)\\s*\\)", sql));
    }

    @Test
    void notJsonArrayContains(){
        final Criterion criterion = CriterionTarget.from("column").notJsonArrayContains(1);
        final String sql = sql((CriterionAttributes) criterion);
        logger.info(sql);
        assertTrue(Pattern.matches("AND !JSON_CONTAINS\\(\\s*column\\s*,\\s*JSON_ARRAY\\(\\s*1\\s*\\)\\s*\\)", sql));
    }

    @Test
    void jsonContainsPath(){
        final Criterion criterion = CriterionTarget.from("column").jsonContainsPath("haha");
        final String sql = sql((CriterionAttributes) criterion);
        logger.info(sql);
        assertTrue(Pattern.matches("AND JSON_CONTAINS_PATH\\(\\s*column\\s*,\\s*'ONE'\\s*,\\s*'haha'\\s*\\)", sql));
    }

    @Test
    void notJsonContainsPath(){
        final Criterion criterion = CriterionTarget.from("column").notJsonContainsPath(new String[]{"haha"});
        final String sql = sql((CriterionAttributes) criterion);
        logger.info(sql);
        assertTrue(Pattern.matches("AND !JSON_CONTAINS_PATH\\(\\s*column\\s*,\\s*'ONE'\\s*,\\s*'haha'\\s*\\)", sql));
    }

}