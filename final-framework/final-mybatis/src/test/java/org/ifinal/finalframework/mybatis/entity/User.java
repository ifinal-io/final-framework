package org.ifinal.finalframework.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.data.PrimaryKey;

/**
 * User.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class User implements IEntity<Long> {

    @PrimaryKey
    private Long id;

    private String name;

    private Integer age;

}
