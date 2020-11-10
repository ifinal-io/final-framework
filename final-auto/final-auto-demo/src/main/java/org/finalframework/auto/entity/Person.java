package org.finalframework.auto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.finalframework.annotation.data.AbsEntity;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/13 15:41:20
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Person extends AbsEntity {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
