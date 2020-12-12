package org.ifinal.finalframework.example.dao.query;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.query.Equal;
import org.ifinal.finalframework.annotation.query.PageQuery;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class PersonQuery extends PageQuery {

    @Equal
    private Integer age;

}
