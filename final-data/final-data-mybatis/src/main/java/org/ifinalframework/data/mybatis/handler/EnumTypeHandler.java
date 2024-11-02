/*
 * Copyright 2020-2021 the original author or authors.
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

import org.ifinalframework.core.IEnum;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.NonNull;

/**
 * <ul>
 *     <li>Set default enum {@linkplain TypeHandler typeHandler} use {@link Configuration#setDefaultEnumTypeHandler(Class)}
 *     by custom {@link ConfigurationCustomizer}.</li>
 *     <li>Declared in mapper file.</li>
 * </ul>
 *
 * @author iimik
 * @version 1.0.0
 * @see Configuration#setDefaultEnumTypeHandler(Class)
 * @see ConfigurationCustomizer
 * @since 1.0.0
 */
public class EnumTypeHandler<E extends Enum<?>> extends BaseTypeHandler<E> {

    private final Map<String, E> cache;

    public EnumTypeHandler(final @NonNull Class<E> type) {
        this.cache = Arrays.stream(type.getEnumConstants()).collect(Collectors.toMap(this::getEnumKey, Function.identity()));
    }

    private String getEnumKey(Enum<?> value) {
        return value instanceof IEnum ? ((IEnum<?>) value).getCode().toString() : value.name();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter instanceof IEnum ? ((IEnum<?>) parameter).getCode() : parameter.name());
    }

    @Override
    public E getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return cache.get(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return cache.get(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return cache.get(cs.getString(columnIndex));
    }

}
