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

package org.ifinalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.type.SimpleType;

import org.ifinalframework.core.IEnum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * BeanEnumPropertySerializerModifierTest.
 *
 * @author ilikly
 * @version 1.4.0
 * @since 1.4.0
 */
@ExtendWith(MockitoExtension.class)
class BeanEnumPropertySerializerModifierTest {

    private final BeanEnumPropertySerializerModifier modifier = new BeanEnumPropertySerializerModifier();

    @Test
    void modifyEnumSerializer() {
        SerializationConfig serializationConfig = new ObjectMapper().getSerializationConfig();
        JavaType javaType = SimpleType.constructUnsafe(EnumBean.class);
        JsonSerializer<?> jsonSerializer = modifier.modifyEnumSerializer(serializationConfig, javaType, mock(BeanDescription.class), null);
        assertEquals(BeanEnumPropertySerializerModifier.EnumCodeSerializer.instance, jsonSerializer);

    }

    @Test
    void support() {
        assertTrue(modifier.support(EnumBean.class));
    }

    @Getter
    @RequiredArgsConstructor
    private enum EnumBean implements IEnum<Integer> {
        V1(1, "V1"), V2(2, "V2"),
        ;
        private final Integer code;
        private final String desc;

    }
}