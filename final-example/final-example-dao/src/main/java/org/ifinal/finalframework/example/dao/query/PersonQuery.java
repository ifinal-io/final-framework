package org.ifinal.finalframework.example.dao.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ifinal.finalframework.query.annotation.Equal;
import org.ifinal.finalframework.query.annotation.Or;
import org.ifinal.finalframework.query.annotation.PageQuery;

import java.io.Serializable;

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
    static class OrQuery implements Serializable {
        @Equal
        private String password;
        @Equal
        private Boolean admin;
    }
}
