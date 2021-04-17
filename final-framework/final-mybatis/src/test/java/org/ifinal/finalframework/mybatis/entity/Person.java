package org.ifinal.finalframework.mybatis.entity;

import org.ifinal.finalframework.core.IEntity;
import org.ifinal.finalframework.data.annotation.PrimaryKey;
import org.ifinal.finalframework.data.annotation.Reference;

import lombok.Getter;
import lombok.Setter;

/**
 * Person.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class Person implements IEntity<Long> {

    @PrimaryKey
    private Long id;

    private String name;

    private Integer age;

    @Reference(properties = {"id", "name"})
    private Person creator;

}
