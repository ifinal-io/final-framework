package org.finalframework.data.query;


import lombok.Getter;
import lombok.Setter;
import org.finalframework.data.entity.AbsEntity;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 23:06:16
 * @since 1.0
 */
@Setter
@Getter
public class QueryEntity extends AbsEntity {
    private String name;
    private Integer age;
}

