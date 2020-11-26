package org.ifinal.finalframework.spiriter.jdbc.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public class RowData extends LinkedHashMap<String, Object> {

    public RowData(ResultSet resultSet) throws SQLException {
        super(resultSet.getMetaData().getColumnCount());
    }

}

