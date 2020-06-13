/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
 * @see java.util.Map
 * @see java.util.List
 * @see java.util.Set
 * @since 1.0
 */
public class JsonTypeHandler extends ParameterTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Json.toJson(parameter));
    }

}

