package org.ifinal.finalframework.annotation.data;

/**
 * @author likly
 * @version 1.0.0
 * @see Table
 * @see Column
 * @since 1.0.0
 */
@FunctionalInterface
public interface NameConverter {

    /**
     * return the mapping name of source name.
     *
     * @param name source name
     * @return mapping name
     */
    String convert(String name);

}
