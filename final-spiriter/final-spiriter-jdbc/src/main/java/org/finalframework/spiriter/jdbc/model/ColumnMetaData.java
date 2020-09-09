

package org.finalframework.spiriter.jdbc.model;


import lombok.Data;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 14:02:11
 * @since 1.0
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

