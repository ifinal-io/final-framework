package org.finalframework.spiriter.jdbc.model;


import lombok.Data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 14:16:25
 * @since 1.0
 */
@Data
public class ResultSets {
    private List<ColumnMetaData> metaData;
    private List<RowData> rowData;

    public static ResultSets from(java.sql.ResultSet resultSet) throws SQLException {
        final ResultSets result = new ResultSets();

        final java.sql.ResultSetMetaData metaData = resultSet.getMetaData();

        List<ColumnMetaData> metaDatas = new ArrayList<>(metaData.getColumnCount());
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            metaDatas.add(ColumnMetaData.from(metaData, i));
        }
        result.setMetaData(metaDatas);


        final List<RowData> list = new ArrayList<>();

        while (resultSet.next()) {
            final RowData rowData = new RowData(resultSet);

            for (ColumnMetaData item : metaDatas) {
                switch (item.getTypeName()) {
                    case "INT":
                    case "BIGINT":
                        rowData.put(item.getName(), resultSet.getLong(item.getIndex()));
                        break;
                    case "DATETIME":
                        rowData.put(item.getName(), Optional.ofNullable(resultSet.getTimestamp(item.getIndex())).map(it -> new Date(it.getTime())).orElse(null));
                        break;
                    default:
                        rowData.put(item.getName(), resultSet.getString(item.getIndex()));
                        break;
                }
            }


            list.add(rowData);
        }


        result.setRowData(list);


        return result;
    }


}

