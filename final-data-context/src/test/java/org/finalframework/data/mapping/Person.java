package org.finalframework.data.mapping;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.finalframework.annotation.data.AbsEntity;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/10 10:22:15
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class Person extends AbsEntity {
    private String name;
    private Integer age;
}
