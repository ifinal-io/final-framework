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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * JsonTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class JsonTest {

    @Data
    private static class JsonBean {

        private Date date = new Date();

        private LocalDateTime localDateTime = LocalDateTime.now();

    }

    @Test
    void toJson() {
        String json = Json.toJson(new JsonBean());
        logger.info(json);
        assertTrue(json.contains("dateFormat"));
        assertTrue(json.contains("localDateTimeFormat"));
    }

    @Test
    void toObject() {
        assertEquals(1, Json.toObject("1"));
        assertEquals(1, Json.toObject("1", Integer.class));
    }

}
