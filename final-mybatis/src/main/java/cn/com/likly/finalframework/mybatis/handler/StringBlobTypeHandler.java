package cn.com.likly.finalframework.mybatis.handler;

import cn.com.likly.finalframework.json.Json;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
public abstract class StringBlobTypeHandler<T> extends BaseTypeHandler<T> {
    private final Charset UTF8 = Charset.forName("utf-8");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        String json = Json.toJson(parameter);
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

    protected abstract T getNullableResult(String string);
}
