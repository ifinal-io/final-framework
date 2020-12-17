package org.ifinal.finalframework.annotation.query;

import org.ifinal.finalframework.annotation.core.lang.Transient;

/**
 * 方向
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

    Direction(final String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
