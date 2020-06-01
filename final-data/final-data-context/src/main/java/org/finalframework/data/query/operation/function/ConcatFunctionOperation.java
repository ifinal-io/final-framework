package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.Operation;
import org.finalframework.data.query.operation.StringOperation;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 12:46:07
 * @since 1.0
 */
public final class ConcatFunctionOperation implements FunctionOperation {
    private final String prefix;
    private final String suffix;

    public ConcatFunctionOperation(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public Operation operation() {
        return StringOperation.CONCAT;
    }

    @Override
    public String apply(Object value) {
        return String.format("CONCAT('%s',%s,'%s')",
                Optional.ofNullable(prefix).orElse(""),
                value,
                Optional.ofNullable(suffix).orElse("")
        );
    }
}

