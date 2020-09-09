

package org.finalframework.data.query.type;


import org.apache.ibatis.type.JdbcType;
import org.finalframework.json.Json;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Converter the parameter to a {@code json} String.
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-11 10:02:27
 * @see org.finalframework.annotation.data.Json
 * @see java.util.Collection
 * @see java.util.Map
 * @see java.util.List
 * @see java.util.Set
 * @since 1.0
 */
public class JsonParameterTypeHandler extends ParameterTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Json.toJson(parameter));
    }

}

