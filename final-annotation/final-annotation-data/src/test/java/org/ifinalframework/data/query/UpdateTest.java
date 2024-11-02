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

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.CriterionExpression;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.data.query.Update;

/**
 * UpdateTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class UpdateTest {

    private static final String COLUMN = "name";

    @Mock
    private QProperty<?> property;

    @BeforeEach
    void beforeEach() {
        Mockito.when(property.getColumn()).thenReturn(COLUMN);
    }

    @Test
    void set() {

        String value = "haha";
        Update update = Update.update().set(property, value);

        Criterion criterion = update.get(0);

        Assertions.assertTrue(criterion instanceof CriterionAttributes);

        CriterionAttributes attributes = (CriterionAttributes) criterion;

        Assertions.assertEquals(COLUMN, attributes.getColumn());
        Assertions.assertEquals(value, attributes.getValue());
        Assertions.assertEquals(CriterionExpression.UPDATE_SET, attributes.getExpression());

        logger.info(attributes.getExpression());

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {2, 4})
    void incr(Integer value) {

        Update update = Update.update();

        if (Objects.isNull(value)) {
            update.inc(property);
        } else {
            update.incr(property, value);
        }

        Criterion criterion = update.get(0);

        Assertions.assertTrue(criterion instanceof CriterionAttributes);

        CriterionAttributes attributes = (CriterionAttributes) criterion;

        Assertions.assertEquals(COLUMN, attributes.getColumn());
        if (Objects.isNull(value)) {
            Assertions.assertEquals(1, attributes.getValue());
        } else {
            Assertions.assertEquals(value, attributes.getValue());
        }
        Assertions.assertEquals(CriterionExpression.UPDATE_INCR, attributes.getExpression());

        logger.info(attributes.getExpression());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {2, 4})
    void decr(Integer value) {

        Update update = Update.update();

        if (Objects.isNull(value)) {
            update.dec(property);
        } else {
            update.decr(property, value);
        }

        Criterion criterion = update.get(0);

        Assertions.assertTrue(criterion instanceof CriterionAttributes);

        CriterionAttributes attributes = (CriterionAttributes) criterion;

        Assertions.assertEquals(COLUMN, attributes.getColumn());
        if (Objects.isNull(value)) {
            Assertions.assertEquals(1, attributes.getValue());
        } else {
            Assertions.assertEquals(value, attributes.getValue());
        }
        Assertions.assertEquals(CriterionExpression.UPDATE_DECR, attributes.getExpression());

        logger.info(attributes.getExpression());
    }

}
