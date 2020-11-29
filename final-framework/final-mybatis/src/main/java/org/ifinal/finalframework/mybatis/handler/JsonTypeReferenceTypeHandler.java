package org.ifinal.finalframework.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.ifinal.finalframework.json.Json;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonTypeReferenceTypeHandler<T> extends BaseTypeReferenceTypeHandler<T> {

    public JsonTypeReferenceTypeHandler(Type type) {
        super(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Json.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toObject(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex));
    }

    private T toObject(String json) {
        if (json == null) {
            return null;
        }
        return Json.toObject(json, getType());
    }
}
