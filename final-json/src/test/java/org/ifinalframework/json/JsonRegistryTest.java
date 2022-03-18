/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.ifinalframework.json.jackson.JacksonJsonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JsonRegistryTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class JsonRegistryTest {

    private JsonRegistry jsonRegistry;

    @BeforeEach
    void beforeEach() {
        jsonRegistry = JsonRegistry.getInstance();
    }

    @Test
    void notNullForDefault() {
        assertNotNull(jsonRegistry.getJsonService());
    }

    @Test
    void shouldThrowNullPointExceptionWhenSetNullJsonService() {
        assertThrows(NullPointerException.class, () -> jsonRegistry.register(null));
    }

    @Test
    void showThrowNullPointExceptionWhenSetNullDefaultJsonService() {
        assertThrows(NullPointerException.class, () -> jsonRegistry.setDefaultJsonService(null));
    }

    @Test
    void customJsonService() {
        JacksonJsonService jsonService = new JacksonJsonService();
        JsonService defaultJsonService = jsonRegistry.getJsonService();
        assertNotEquals(jsonService, defaultJsonService);
        jsonRegistry.setDefaultJsonService(jsonService);
        assertEquals(jsonService, jsonRegistry.getJsonService());
        jsonRegistry.register(defaultJsonService);
        assertEquals(defaultJsonService, jsonRegistry.getJsonService());

    }

}
