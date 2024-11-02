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

import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BaseTypeReferenceTypeHandlerTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class BaseTypeReferenceTypeHandlerTest {

    @Test
    void getType() {
        BaseTypeReferenceTypeHandler typeHandler = new JsonTypeReferenceTypeHandler(String.class) {
        };
        Assertions.assertEquals(String.class, typeHandler.getType());

        typeHandler = new BaseTypeReferenceTypeHandler<Integer>() {
            @Override
            public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {

            }

            @Override
            public Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
                return null;
            }

            @Override
            public Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
                return null;
            }

            @Override
            public Integer getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
                return null;
            }
        };
        Assertions.assertEquals(Integer.class, typeHandler.getType());


    }


}