/*
 * Copyright 2020-2024 the original author or authors.
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

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Service;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

import static org.junit.jupiter.api.Assertions.*;

/**
 * VelocitiesTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
class VelocitiesTest {


    @Test
    void getValue() {
        Map<String, Object> context = new HashMap<>();
        context.put("name", "xiaoMing");
        context.put("age", 12);
        context.put("xiaoshu", 1);
        assertEquals("xiaoMing", Velocities.eval("${name}", context));
        assertEquals("12", Velocities.eval("${age}", context));

    }

    @Test
    void getValueFromAnnotationAttribute() {
        AnnotationAttributes attributes = new AnnotationAttributes(Service.class);
        attributes.put("value", "haha");
        assertEquals("haha", Velocities.eval("${value}", attributes));
    }

    @Test
    void getValueFromObject() {
        Params params = new Params("xiaoMing", 12);
        assertEquals("xiaoMing", Velocities.eval("${name}", params));
        assertEquals("12", Velocities.eval("${age}", params));
    }

    @Data
    @AllArgsConstructor
    private static class Params {

        private String name;
        private Integer age;

    }

}
