package org.finalframework.data.query.type;


import org.apache.ibatis.type.BaseTypeHandler;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@link ParameterTypeHandler} only used on {@literal WHERE} fragment.
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-11 10:05:06
 * @since 1.0
 */
public abstract class ParameterTypeHandler<T> extends BaseTypeHandler<T> {


    @Override
    public final T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        throw new UnsupportedOperationException("");
    }

    @Override
    public final T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("");

    }

    @Override
    public final T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("");

    }
}

