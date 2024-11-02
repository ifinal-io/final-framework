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

import org.apache.ibatis.mapping.BoundSql;

import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.CriterionTarget;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CompareConditionTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
class CompareConditionTest extends ConditionTest{
    @Test
    void test(){
        assertTrue(Pattern.matches("AND name\\s*=\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").eq("123"))));
        assertTrue(Pattern.matches("AND name\\s*!=\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").neq("123"))));
        assertTrue(Pattern.matches("AND name\\s*>\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").gt("123"))));
        assertTrue(Pattern.matches("AND name\\s*>=\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").geq("123"))));
        assertTrue(Pattern.matches("AND name\\s*<\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").lt("123"))));
        assertTrue(Pattern.matches("AND name\\s*<=\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").leq("123"))));
        assertTrue(Pattern.matches("AND name\\s*<\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").before("123"))));
        assertTrue(Pattern.matches("AND name\\s*>\\s*'123'\\s*", sql((CriterionAttributes) CriterionTarget.from("name").after("123"))));
    }


}