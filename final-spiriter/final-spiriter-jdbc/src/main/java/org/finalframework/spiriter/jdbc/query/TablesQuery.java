

package org.finalframework.spiriter.jdbc.query;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 23:05:50
 * @since 1.0
 */
@Data
public class TablesQuery {
    @NotEmpty
    private String schema;
}

