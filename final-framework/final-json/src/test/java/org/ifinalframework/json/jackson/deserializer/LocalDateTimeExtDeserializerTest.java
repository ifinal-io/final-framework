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

package org.ifinalframework.json.jackson.deserializer;

import org.ifinalframework.json.Json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * LocalDateTimeDeserializerTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class LocalDateTimeExtDeserializerTest {

    @Setter
    @Getter
    private static class LocalDateTimeBean {
        private LocalDateTime localDateTime;
        private LocalDateTime now;
    }

    @Test
    void deserialize() throws IOException {
        final String json = "{\"localDateTime\":\"2022-10-31 00:10:29\",\"now\": " + System.currentTimeMillis() + "}";
        LocalDateTimeBean localDateTimeBean = Json.toObject(json, LocalDateTimeBean.class);


    }

}
