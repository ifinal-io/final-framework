

package org.finalframework.mybatis.handler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.data.geo.Point;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 14:20:45
 * @since 1.0
 */
public class PointTypeHandler extends BaseTypeHandler<Point> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.format("POINT(%f %f)", parameter.getX(), parameter.getY()));
    }

    @Override
    public Point getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Point getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Point getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}

