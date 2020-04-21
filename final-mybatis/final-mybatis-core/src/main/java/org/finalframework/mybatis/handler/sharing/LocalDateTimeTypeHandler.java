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
import org.finalframework.spring.annotation.factory.SpringComponent;
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

