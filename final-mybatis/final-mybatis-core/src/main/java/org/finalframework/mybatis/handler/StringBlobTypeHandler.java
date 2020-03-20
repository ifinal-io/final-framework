package org.finalframework.mybatis.handler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.json.Json;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
@Deprecated
public abstract class StringBlobTypeHandler<T> extends BaseTypeHandler<T> {

    private final Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    public final void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        String json = setNonNullParameter(parameter);
        if (json != null) {
            ps.setBytes(i, json.getBytes(UTF8));
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseBytes(rs.getBytes(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseBytes(rs.getBytes(columnIndex));

    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseBytes(cs.getBytes(columnIndex));
    }

    private T parseBytes(byte[] bytes) {
        if (bytes == null) return null;
        final String string = new String(bytes, UTF8);
        return getNullableResult(string);
    }

    protected String setNonNullParameter(T parameter) {
        return Json.toJson(parameter);
    }

    protected abstract T getNullableResult(String string);
}
