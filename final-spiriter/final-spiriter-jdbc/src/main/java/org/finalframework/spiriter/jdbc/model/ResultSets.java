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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
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

