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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.*;

/**
 * ClassJsonSerializerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ClassJsonSerializerTest {

    @Test
    void serialize() {

        assertDoesNotThrow(() -> {

            ClassJsonSerializer jsonSerializer = new ClassJsonSerializer();
            JsonGenerator jsonGenerator = mock(JsonGenerator.class);

            jsonSerializer.serialize(
                ClassJsonSerializerTest.class,
                jsonGenerator,
                null);
            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            verify(jsonGenerator).writeString(argumentCaptor.capture());
//            verify(jsonGenerator).writeString(eq);
            String capture = argumentCaptor.getValue();
            assertEquals(ClassJsonSerializerTest.class.getName(), capture);
            logger.info(capture);

        });
    }

    @Test
    void handleType() {
        ClassJsonSerializer jsonSerializer = new ClassJsonSerializer();
        assertEquals(Class.class, jsonSerializer.handledType());
    }

}
