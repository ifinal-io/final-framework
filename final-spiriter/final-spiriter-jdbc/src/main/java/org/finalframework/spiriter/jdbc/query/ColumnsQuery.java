

package org.finalframework.spiriter.jdbc.query;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 20:46:14
 * @since 1.0
 */
@Data
public class ColumnsQuery {
    @NotEmpty
    private String schema;
    @NotEmpty
    private String table;
}

