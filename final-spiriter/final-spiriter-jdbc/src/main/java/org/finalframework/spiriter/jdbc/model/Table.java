package org.finalframework.spiriter.jdbc.model;


import lombok.Data;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:48:34
 * @since 1.0
 */
@Data
public class Table {
    private String logicTable;
    private List<String> actualTables;
}

