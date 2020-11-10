package org.finalframework.auto.entity;

import lombok.Data;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.data.AutoInc;
import org.finalframework.annotation.data.PrimaryKey;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/13 15:43:08
 * @since 1.0
 */
@Data
public class Student implements IEntity<Long> {
    @AutoInc
    @PrimaryKey
    private Long id;
    private String name;
    private String age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
