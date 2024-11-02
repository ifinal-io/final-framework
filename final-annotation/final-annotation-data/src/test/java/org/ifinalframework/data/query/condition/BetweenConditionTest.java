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

import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.CriterionTarget;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BetweenConditionTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class BetweenConditionTest extends ConditionTest{
    @Test
    void test(){
        assertTrue(Pattern.matches("AND name\\s*BETWEEN\\s*1\\s*AND\\s*2\\s*", sql((CriterionAttributes) CriterionTarget.from("name").between(1,2))));
        assertTrue(Pattern.matches("AND name\\s*NOT BETWEEN\\s*1\\s*AND\\s*2\\s*", sql((CriterionAttributes) CriterionTarget.from("name").notBetween(1,2))));
    }

}