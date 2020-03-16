package org.finalframework.data.entity;


import org.finalframework.data.annotation.IUser;
import org.finalframework.data.annotation.PrimaryKey;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:03:16
 * @since 1.0
 */
public class AbsUser implements IUser<Long> {
    @PrimaryKey
    private Long id;
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

