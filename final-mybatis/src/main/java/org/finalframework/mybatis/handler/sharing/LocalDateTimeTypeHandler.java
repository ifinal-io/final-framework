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

package org.finalframework.mybatis.handler.sharing;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.core.converter.Converter;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.util.Dates;

/**
 * 解决{@linkplain Sharding-JDBC}不支持JAVA8时间问题
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-13 17:06:25
 * @since 1.0
 */
@SpringComponent
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> implements Converter<Date, LocalDateTime> {

    private static final DateTypeHandler DATE_TYPE_HANDLER = new DateTypeHandler();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        DATE_TYPE_HANDLER
                .setParameter(ps, i, Dates.to(parameter), jdbcType);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(DATE_TYPE_HANDLER.getResult(rs, columnName));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(DATE_TYPE_HANDLER.getResult(rs, columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(DATE_TYPE_HANDLER.getResult(cs, columnIndex));
    }

    @Override
    public LocalDateTime convert(Date date) {
        return Dates.from(date);

    }
}

