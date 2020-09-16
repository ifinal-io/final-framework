package org.finalframework.annotation.data;


import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 13:15:52
 * @since 1.0
 */
@FunctionalInterface
public interface NameConverter extends Function<String, String> {

    /**
     * return the mapping name of source name.
     *
     * @param name source name
     * @return mapping name
     */
    String convert(String name);

    @Override
    default String apply(String name) {
        return convert(name);
    }
}
