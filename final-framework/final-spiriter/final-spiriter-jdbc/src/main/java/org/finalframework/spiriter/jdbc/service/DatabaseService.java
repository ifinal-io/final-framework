package org.ifinal.finalframework.spiriter.jdbc.service;


import org.ifinal.finalframework.spiriter.jdbc.model.Table;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface DatabaseService {

    List<Table> showTables(DataSource dataSource) throws SQLException;

}

