package org.finalframework.core.generator;

import java.util.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-27 14:52:41
 * @since 1.0
 */
public class UUIDTraceGenerator implements TraceGenerator {

    public static final UUIDTraceGenerator instance = new UUIDTraceGenerator();


    @Override
    public String generate(Void data) {
        return UUID.randomUUID().toString();
    }
}
