package org.finalframework.data.query.enums;

import org.finalframework.data.annotation.Transient;

/**
 * 方向
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:17
 * @since 1.0
 */
@Transient
public enum Direction {
    /**
     * 正序
     */
    ASC("ASC"),
    /**
     * 倒序
     */
    DESC("DESC");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
