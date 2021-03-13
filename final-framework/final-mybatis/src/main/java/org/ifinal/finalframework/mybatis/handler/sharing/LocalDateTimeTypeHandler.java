package org.ifinal.finalframework.mybatis.handler.sharing;

import org.springframework.stereotype.Component;

import org.ifinal.finalframework.util.Dates;
import org.ifinal.finalframework.util.function.Converter;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 解决{@code Sharding-JDBC}不支持JAVA8时间问题
 *
 * @author likly
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

        DATE_TYPE_HANDLER
            .setParameter(ps, i, Dates.to(parameter), jdbcType);
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

