package org.finalframework.data.query.type;


import org.apache.ibatis.type.JdbcType;
import org.finalframework.json.Json;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-11 10:02:27
 * @see org.finalframework.data.annotation.Json
 * @see java.util.Collection
 * @since 1.0
 */
public class JsonTypeHandler extends ParameterTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Json.toJson(parameter));
    }

}

