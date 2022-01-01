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

package org.ifinalframework.velocity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * VelocitiesTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class VelocitiesTest {


    @Test
    void getValue() {
        Map<String, Object> context = new HashMap<>();
        context.put("name", "xiaoMing");
        context.put("age", 12);
        assertEquals("xiaoMing", Velocities.getValue("${name}", context));
        assertEquals("12", Velocities.getValue("${age}", context));
    }

    @Test
    void getValueFromObject() {
        Params params = new Params("xiaoMing", 12);
        assertEquals("xiaoMing", Velocities.getValue("${name}", params));
        assertEquals("12", Velocities.getValue("${age}", params));
    }

    @Data
    @AllArgsConstructor
    private static class Params {

        private String name;

        private Integer age;

    }

}
