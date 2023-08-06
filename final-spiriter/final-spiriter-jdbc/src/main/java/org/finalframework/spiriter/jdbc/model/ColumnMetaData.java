/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.spiriter.jdbc.model;


import lombok.Data;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class ColumnMetaData {
    private Integer index;
    private String schema;
    private String table;
    private String name;
    private String label;
    private Integer type;
    private String typeName;

    public static ColumnMetaData from(ResultSetMetaData metaData, Integer index) throws SQLException {
        final ColumnMetaData columnMetaData = new ColumnMetaData();
        columnMetaData.setIndex(index);
        columnMetaData.setSchema(metaData.getSchemaName(index));
        columnMetaData.setTable(metaData.getTableName(index));
        columnMetaData.setName(metaData.getColumnName(index));
        columnMetaData.setLabel(metaData.getColumnLabel(index));
        columnMetaData.setType(metaData.getColumnType(index));
        columnMetaData.setTypeName(metaData.getColumnTypeName(index));
        return columnMetaData;
    }
}

