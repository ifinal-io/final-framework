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

package org.ifinalframework.json.jackson.serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

/**
 * LocalDateSerializerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class LocalDateSerializerTest {

    private final LocalDateSerializer serializer = new LocalDateSerializer();

    @Mock
    private JsonGenerator jsonGenerator;

    @Test
    void handleType() {
        Assertions.assertEquals(LocalDate.class, serializer.handledType());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "2021-10-01")
    void serialize(LocalDate localDate) throws IOException {

        serializer.serialize(localDate, jsonGenerator, null);

        Assumptions
            .assumingThat(Objects.isNull(localDate), () -> Mockito.verify(jsonGenerator, Mockito.times(1)).writeNull());
        Assumptions.assumingThat(Objects.nonNull(localDate),
            () -> Mockito.verify(jsonGenerator, Mockito.times(1)).writeString(Mockito.anyString()));

    }

}
