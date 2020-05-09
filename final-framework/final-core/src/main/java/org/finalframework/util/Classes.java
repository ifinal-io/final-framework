package org.finalframework.util;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 11:25:00
 * @since 1.0
 */
public interface Classes {

    static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
