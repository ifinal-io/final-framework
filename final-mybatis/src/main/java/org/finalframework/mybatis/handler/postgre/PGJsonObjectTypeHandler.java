

package org.finalframework.mybatis.handler.postgre;

import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.json.Json;
import org.postgresql.util.PGobject;

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
public class PGJsonObjectTypeHandler<T> extends BaseTypeHandler<T> {
    private final Class<T> type;

    public PGJsonObjectTypeHandler(@NonNull Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        PGobject object = new PGobject();
        object.setType("json");
        object.setValue(Json.toJson(parameter));
        ps.setObject(i, object);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        if (json == null || json.trim().isEmpty()) return null;
        return Json.toObject(json, type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        if (json == null || json.trim().isEmpty()) return null;
        return Json.toObject(json, type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        if (json == null || json.trim().isEmpty()) return null;
        return Json.toObject(json, type);
    }
}
