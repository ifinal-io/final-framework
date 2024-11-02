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

package org.ifinalframework.data.query.type;

import org.apache.ibatis.type.BaseTypeHandler;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@link ParameterTypeHandler} only used on {@literal WHERE} fragment.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class ParameterTypeHandler<T> extends BaseTypeHandler<T> {

    @Override
    public final T getNullableResult(final ResultSet rs, final String columnName) throws SQLException {

        throw new UnsupportedOperationException("");
    }

    @Override
    public final T getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {

        throw new UnsupportedOperationException("");

    }

    @Override
    public final T getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {

        throw new UnsupportedOperationException("");

    }

}

