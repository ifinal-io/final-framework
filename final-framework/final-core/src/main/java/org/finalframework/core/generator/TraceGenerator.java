package org.finalframework.core.generator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-27 14:52:00
 * @since 1.0
 */
public interface TraceGenerator extends Generator<Void, String> {

    default String generate() {
        return generate(null);
    }

}
