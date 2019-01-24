package com.ilikly.finalframework.data.annotation.enums;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 16:02
 * @since 1.0
 */
public enum PrimaryKeyType {
    /**
     * the primary key use auto incr.
     */
    AUTO_INC,
    /**
     * the primary key use UUID()
     */
    UUID,
    /**
     * the primary key use MD5(UUID())
     */
    UUID_MD5,
    OTHER
}
