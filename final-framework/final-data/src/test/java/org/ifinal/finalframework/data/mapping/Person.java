package org.ifinal.finalframework.data.mapping;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ifinal.finalframework.annotation.data.AbsEntity;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Person extends AbsEntity {
    private String name;
    private Integer age;
    private List<Integer> intList;
}
