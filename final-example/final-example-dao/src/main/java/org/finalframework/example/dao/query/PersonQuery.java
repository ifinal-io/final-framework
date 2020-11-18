package org.finalframework.example.dao.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.finalframework.annotation.query.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/18 10:12:12
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonQuery extends PageQuery {
    @Limit
    public Integer limit;
    @Like
    private String name;
    @Between
    private BetweenValue<Integer> age;
}
