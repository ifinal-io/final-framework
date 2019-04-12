package org.finalframework.mybatis.handler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.data.query.Order;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-12 11:38:29
 * @since 1.0
 */
public class OrderTypeHandler extends BaseTypeHandler<Order> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Order parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Order getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Order getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Order getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
