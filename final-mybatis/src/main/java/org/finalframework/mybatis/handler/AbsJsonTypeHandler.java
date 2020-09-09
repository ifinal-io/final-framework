

package org.finalframework.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.json.Json;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 * @see JsonObjectTypeHandler
 * @see JsonListTypeHandler
 * @see JsonSetTypeHandler
 */
public abstract class AbsJsonTypeHandler<T> extends BaseTypeHandler<T> {

    @Override
    public final void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, setNonNullParameter(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        if (json == null || json.trim().isEmpty()) return null;
        return getNullableResult(json);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        if (json == null || json.trim().isEmpty()) return null;
        return getNullableResult(json);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        if (json == null || json.trim().isEmpty()) return null;
        return getNullableResult(json);
    }


    protected String setNonNullParameter(T parameter) {
        return Json.toJson(parameter);
    }

    protected abstract T getNullableResult(String json);
}
