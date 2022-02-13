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

package org.ifinalframework.json;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        private Date date = new Date(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());

        private LocalDate localDate = LocalDate.now();
        private LocalDateTime localDateTime = LocalDateTime.now().toLocalDate().atStartOfDay();


    }

    @Test
    void toJson() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC.normalized()));
        LocaleContextHolder.setTimeZone(TimeZone.getTimeZone(ZoneOffset.of("+8")));
        String json = Json.toJson(new JsonBean());
        logger.info(json);
        assertTrue(json.contains("dateFormat"));
        assertTrue(json.contains("localDateTimeFormat"));

        final JsonBean jsonBean = Json.toObject(json, JsonBean.class);
        assertEquals(LocalDate.now().atStartOfDay(), jsonBean.localDateTime);

    }

    @Test
    void toObject() {
        assertEquals(1, Json.toObject("1"));
        assertEquals(1, Json.toObject("1", Integer.class));
    }

    @Test
    void toJsonEmptyMap() {
        final Map<String, Object> map = Collections.singletonMap("key", null);
        assertEquals("{}", Json.toJson(map));
    }

}
