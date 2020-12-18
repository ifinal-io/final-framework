package org.ifinal.finalframework.annotation.query;

/**
 * 方向
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
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
