package org.ifinal.finalframework.spiriter.jdbc.model;


import lombok.Data;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Data
public class Table {
    private String logicTable;
    private List<String> actualTables;
}

