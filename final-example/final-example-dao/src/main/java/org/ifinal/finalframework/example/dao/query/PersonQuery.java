package org.ifinal.finalframework.example.dao.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ifinal.finalframework.annotation.query.Equal;
import org.ifinal.finalframework.annotation.query.Or;
import org.ifinal.finalframework.annotation.query.PageQuery;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonQuery extends PageQuery {
    @Equal
    private String name;

    @Or
    private OrQuery orQuery;

    @Data
    static class OrQuery {
        @Equal
        private String password;
        @Equal
        private Boolean admin;
    }
}
