/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.mybatis.handler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.json.Json;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
@Deprecated
public abstract class StringBlobTypeHandler<T> extends BaseTypeHandler<T> {

    private final Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    public final void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        String json = setNonNullParameter(parameter);
        if (json != null) {
            ps.setBytes(i, json.getBytes(UTF8));
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseBytes(rs.getBytes(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseBytes(rs.getBytes(columnIndex));

    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseBytes(cs.getBytes(columnIndex));
    }

    private T parseBytes(byte[] bytes) {
        if (bytes == null) return null;
        final String string = new String(bytes, UTF8);
        return getNullableResult(string);
    }

    protected String setNonNullParameter(T parameter) {
        return Json.toJson(parameter);
    }

    protected abstract T getNullableResult(String string);
}
