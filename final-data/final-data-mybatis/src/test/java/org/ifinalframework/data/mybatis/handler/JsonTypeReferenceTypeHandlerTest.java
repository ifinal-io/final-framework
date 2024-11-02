/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.mybatis.handler;

import org.ifinalframework.json.Json;

import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * JsonTypeReferenceTypeHandlerTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@ExtendWith(MockitoExtension.class)
class JsonTypeReferenceTypeHandlerTest {

    private final JsonTypeReferenceTypeHandler<Map<String, Object>> handler = new JsonTypeReferenceTypeHandler(Map.class);
    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;
    @Mock
    private CallableStatement cs;

    @Test
    @SneakyThrows
    void setNull() {
        handler.setParameter(ps, 1, null, JdbcType.VARCHAR);
        verify(ps, only()).setNull(anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void setNonNullParameter() {


        Map<String, Object> parameter = Collections.singletonMap("name", "value");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        assertDoesNotThrow(() -> handler.setParameter(ps, 1, parameter, JdbcType.VARCHAR));
        verify(ps, only()).setString(anyInt(), captor.capture());
        assertEquals(Json.toJson(parameter), captor.getValue());
    }

    @Test
    @SneakyThrows
    void getNullableResult() {
        Map<String, Object> parameter = Collections.singletonMap("name", "value");
        when(rs.getString(1)).thenReturn(Json.toJson(parameter));
        Map<String, Object> result = handler.getResult(rs, 1);
        assertEquals("value", parameter.get("name"));

        when(rs.getString("yn")).thenReturn(Json.toJson(parameter));
        result = handler.getResult(rs, "yn");
        assertEquals("value", parameter.get("name"));

        when(cs.getString(1)).thenReturn(Json.toJson(parameter));
        assertEquals("value", handler.getResult(cs, 1).get("name"));
    }


}