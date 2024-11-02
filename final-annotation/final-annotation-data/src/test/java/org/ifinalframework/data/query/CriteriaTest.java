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

package org.ifinalframework.data.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ifinalframework.data.query.AndOr;
import org.ifinalframework.data.query.Criteria;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.CriterionExpression;
import org.ifinalframework.data.query.CriterionTarget;

/**
 * CriteriaTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
class CriteriaTest {

    @Test
    void and() {
        Criteria criteria = Criteria.and(CriterionTarget.from("name").eq("hah"));
        Assertions.assertEquals(AndOr.AND, criteria.getAndOr());
        CriterionAttributes criterion = (CriterionAttributes) criteria.get(0);
        assertEquals("name", criterion.getColumn());
        assertEquals(CriterionExpression.EQUAL, criterion.getExpression());
    }

    @Test
    void or() {
        Criteria criteria = Criteria.or(CriterionTarget.from("name").eq("hah"));
        Assertions.assertEquals(AndOr.OR, criteria.getAndOr());
        CriterionAttributes criterion = (CriterionAttributes) criteria.get(0);
        assertEquals("name", criterion.getColumn());
        assertEquals(CriterionExpression.EQUAL, criterion.getExpression());
    }

}
