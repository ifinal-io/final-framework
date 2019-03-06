package com.ilikly.finalframework.mybatis.handler;


import com.ilikly.finalframework.data.query.Criteria;
import com.ilikly.finalframework.data.query.builder.CriteriaSqlBuilder;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-06 15:04:17
 * @since 1.0
 */
public class CriteriaTypeHandler extends BaseTypeHandler<Criteria> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Criteria parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) return;
        final String sql = new CriteriaSqlBuilder(parameter).build();
        ps.setString(i, sql);
    }

    @Override
    public Criteria getNullableResult(ResultSet rs, String columnName) throws SQLException {
        throw new UnsupportedOperationException(getClass().getName());
    }

    @Override
    public Criteria getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException(getClass().getName());
    }

    @Override
    public Criteria getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException(getClass().getName());
    }
}
