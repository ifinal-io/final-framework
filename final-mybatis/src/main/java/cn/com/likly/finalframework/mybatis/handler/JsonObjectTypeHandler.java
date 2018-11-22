package cn.com.likly.finalframework.mybatis.handler;

import cn.com.likly.finalframework.json.Json;
import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

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
public class JsonObjectTypeHandler<T> extends BaseTypeHandler<T> {
    private final Class<T> type;

    public JsonObjectTypeHandler(@NonNull Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Json.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        if (json == null || json.trim().isEmpty()) return null;
        return Json.parse(json, type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        if (json == null || json.trim().isEmpty()) return null;
        return Json.parse(json, type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        if (json == null || json.trim().isEmpty()) return null;
        return Json.parse(json, type);
    }
}
