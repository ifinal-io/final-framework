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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.context.i18n.LocaleContextHolder;

import org.ifinalframework.core.IEnum;
import org.ifinalframework.data.annotation.EnumValue;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class JsonTest {

    @Data
    private static class JsonBean {

        @JsonView(Views.V1.class)
        private Date date;

        @JsonView(Views.V2.class)
        private LocalDate localDate;
        @JsonView(Views.V2.class)
        private LocalDateTime localDateTime;
        @EnumValue(Yn.class)
        private Integer yn;


        public static class Views {
            public interface V1 {
            }

            public interface V2 {
            }
        }


    }

    private JsonBean newJsonBean() {
        JsonBean jsonBean = new JsonBean();
        jsonBean.date = new Date(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
        jsonBean.localDateTime = LocalDateTime.now().toLocalDate().atStartOfDay();
        jsonBean.localDate = LocalDate.now();
        return jsonBean;
    }

    @Test
    void toJson() {
        logger.info(Json.toJson(LocalDate.now()));
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC.normalized()));
        LocaleContextHolder.setTimeZone(TimeZone.getTimeZone(ZoneOffset.of("+8")));
        JsonBean jsonBean = newJsonBean();
        jsonBean.setYn(Yn.YES.code);
        String json = Json.toJson(jsonBean);
        logger.info(json);
        assertTrue(json.contains("dateFormat"));
        assertTrue(json.contains("localDateTimeFormat"));

        jsonBean = Json.toObject(json, JsonBean.class);
        assertEquals(LocalDate.now().atStartOfDay(), jsonBean.localDateTime);

    }

    @Test
    void toJsonWithView() {
        JsonBean jsonBean = newJsonBean();
        String json = Json.toJson(jsonBean, JsonBean.Views.V1.class);
        assertTrue(json.contains("date"));
        assertFalse(json.contains("localDate"));
    }

    @Test
    void toObject() {
        LocalDate localDate = Json.toObject("\"2023-02-01\"", LocalDate.class);
        assertEquals(1, Json.toObject("1"));
        assertEquals(1, Json.toObject("1", Integer.class));
    }

    @Test
    void toObjectOfString() {
        assertEquals("haha", Json.toObject("haha", String.class));
    }

    @Test
    @SneakyThrows
    void toObjectWithView() {
        JsonBean jsonBean = newJsonBean();
        String json = Json.toJson(jsonBean);
        JsonBean value = Json.toObject(json, JsonBean.class, JsonBean.Views.V2.class);
        assertNull(value.date);
        assertNotNull(value.localDateTime);
    }

    @Test
    void toJsonEmptyMap() {
        final Map<String, Object> map = Collections.singletonMap("key", null);
        assertEquals("{}", Json.toJson(map));
    }


    @AllArgsConstructor
    @Getter
    public enum Yn implements IEnum<Integer> {
        YES(1, "Y"), NO(0, "N");

        private final Integer code;
        private final String desc;

        public static Yn valueOf(final Integer code) {
            return Arrays.stream(Yn.values()).filter(it -> it.getCode().equals(code)).findFirst().orElse(null);
        }

    }

}
