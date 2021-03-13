package org.ifinal.finalframework.data.query.type;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;

/**
 * The {@link ParameterTypeHandler} only used on {@literal WHERE} fragment.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class ParameterTypeHandler<T> extends BaseTypeHandler<T> {

    @Override
    public final T getNullableResult(final ResultSet rs, final String columnName) throws SQLException {

        throw new UnsupportedOperationException("");
    }

    @Override
    public final T getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {

        throw new UnsupportedOperationException("");

    }

    @Override
    public final T getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {

        throw new UnsupportedOperationException("");

    }

}

