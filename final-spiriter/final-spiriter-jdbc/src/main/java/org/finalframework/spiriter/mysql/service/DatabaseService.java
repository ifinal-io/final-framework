package org.finalframework.spiriter.mysql.service;


import org.finalframework.spiriter.mysql.model.Table;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:48:15
 * @since 1.0
 */
public interface DatabaseService {

    List<Table> showTables(DataSource dataSource) throws SQLException;

}

