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

package org.ifinalframework.data.mybatis.handler.sharing;

import org.springframework.stereotype.Component;

import org.ifinalframework.util.Dates;
import org.ifinalframework.util.function.Converter;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 解决{@code Sharding-JDBC}不支持JAVA8时间问题
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> implements Converter<Date, LocalDateTime> {

    private static final DateTypeHandler DATE_TYPE_HANDLER = new DateTypeHandler();

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final LocalDateTime parameter,
                                    final JdbcType jdbcType)
            throws SQLException {
        DATE_TYPE_HANDLER.setParameter(ps, i, Dates.to(parameter), jdbcType);
    }

    @Override
    public LocalDateTime getNullableResult(final ResultSet rs, final String columnName) throws SQLException {

        return convert(DATE_TYPE_HANDLER.getResult(rs, columnName));
    }

    @Override
    public LocalDateTime getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {

        return convert(DATE_TYPE_HANDLER.getResult(rs, columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {

        return convert(DATE_TYPE_HANDLER.getResult(cs, columnIndex));
    }

    @Override
    public LocalDateTime convert(final Date date) {

        return Dates.from(date);

    }

}

