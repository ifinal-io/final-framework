package org.ifinal.finalframework.data.query.type;

import org.ifinal.finalframework.json.Json;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

/**
 * Converter the parameter to a {@code json} String.
 *
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.data.annotation.Json
 * @see java.util.Collection
 * @see java.util.Map
 * @see java.util.List
 * @see java.util.Set
 * @since 1.0.0
 */
public class JsonParameterTypeHandler extends ParameterTypeHandler<Object> {

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final Object parameter,
        final JdbcType jdbcType) throws SQLException {

        ps.setString(i, Json.toJson(parameter));
    }

}

