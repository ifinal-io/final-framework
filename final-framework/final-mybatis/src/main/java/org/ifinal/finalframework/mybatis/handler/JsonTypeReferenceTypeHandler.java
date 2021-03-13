package org.ifinal.finalframework.mybatis.handler;

import org.ifinal.finalframework.json.Json;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonTypeReferenceTypeHandler<T> extends BaseTypeReferenceTypeHandler<T> {

    public JsonTypeReferenceTypeHandler(final Type type) {

        super(type);
    }

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final T parameter, final JdbcType jdbcType)
        throws SQLException {

        ps.setString(i, Json.toJson(parameter));
    }

    @Override
    public T getNullableResult(final ResultSet rs, final String columnName) throws SQLException {

        return toObject(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {

        return toObject(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {

        return toObject(cs.getString(columnIndex));
    }

    private T toObject(final String json) {

        if (json == null) {
            return null;
        }
        return Json.toObject(json, getType());
    }

}
