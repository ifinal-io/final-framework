package com.ilikly.finalframework.redis;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:50:10
 * @since 1.0
 */
public interface ValueOperation {
    boolean set(String key, Object value);
}
