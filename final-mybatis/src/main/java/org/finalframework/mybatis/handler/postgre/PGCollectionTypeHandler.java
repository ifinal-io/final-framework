

package org.finalframework.mybatis.handler.postgre;

import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
public abstract class PGCollectionTypeHandler<E, T extends Collection<E>> extends BaseTypeHandler<T> {

    private final Class<E> type;

    public PGCollectionTypeHandler(@NonNull Class<E> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        PGobject object = new PGobject();
        object.setType("json");
        object.setValue(setParameter(parameter));
        ps.setObject(i, object);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value == null || value.trim().isEmpty()) return null;
        return getResult(value.trim(), type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (value == null || value.trim().isEmpty()) return null;
        return getResult(value.trim(), type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (value == null || value.trim().isEmpty()) return null;
        return getResult(value.trim(), type);
    }

    protected abstract String setParameter(T parameter);

    protected abstract T getResult(String value, Class type);
}
