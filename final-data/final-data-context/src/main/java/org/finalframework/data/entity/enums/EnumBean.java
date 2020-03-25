package org.finalframework.data.entity.enums;

import org.finalframework.data.annotation.IEnum;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-27 10:20:49
 * @since 1.0
 */
public class EnumBean<T> implements Serializable {
    private T code;
    private String name;

    public EnumBean() {
    }

    public EnumBean(IEnum<T> iEnum) {
        this(iEnum.getCode(), iEnum.getDesc());
    }

    public EnumBean(T code, String name) {
        this.code = code;
        this.name = name;
    }

    public T getCode() {
        return code;
    }

    public void setCode(T code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
