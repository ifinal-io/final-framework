

package org.finalframework.spiriter.jdbc.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 14:22:31
 * @since 1.0
 */
public class RowData extends LinkedHashMap<String, Object> {

    public RowData(ResultSet resultSet) throws SQLException {
        super(resultSet.getMetaData().getColumnCount());
    }

}

