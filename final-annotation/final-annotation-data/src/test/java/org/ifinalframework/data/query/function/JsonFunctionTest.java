/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.query.function;

import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.CriterionTarget;
import org.ifinalframework.velocity.Velocities;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JsonFunctionTest.
 *
 * @author iimik
 * @version 1.3.5
 * @since 1.3.5
 */
@Slf4j
class JsonFunctionTest {

    @Test
    void jsonUnquote() {
        CriterionAttributes criterion = (CriterionAttributes) CriterionTarget.from("name").jsonUnquote().eq(1);
        assertEquals("JSON_UNQUOTE(name)", criterion.getColumn());
        logger.info(criterion.getExpression());
        String eval = Velocities.eval(criterion.getExpression(), criterion);
        logger.info(eval);

    }
}