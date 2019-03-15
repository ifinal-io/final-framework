package com.ilikly.finalframework.mybatis.handler;


import com.ilikly.finalframework.data.query.Sort;
import com.ilikly.finalframework.data.query.builder.SortSqlBuilder;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-06 14:37:15
 * @since 1.0
 */
public class SortTypeHandler extends BaseTypeHandler<Sort> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Sort parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) return;
        final String sql = new SortSqlBuilder(parameter).build();
        ps.setString(i, sql);
    }

    @Override
    public Sort getNullableResult(ResultSet rs, String columnName) throws SQLException {
        throw new UnsupportedOperationException(getRawType().getTypeName());
    }

    @Override
    public Sort getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException(getRawType().getTypeName());
    }

    @Override
    public Sort getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException(getRawType().getTypeName());
    }
}
