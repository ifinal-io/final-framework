package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionOperation;
import org.finalframework.data.query.criterion.operator.FunctionOperator;
import org.finalframework.data.query.criterion.function.FunctionOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 00:54:27
 * @since 1.0
 */

public class BaseFunctionOperations<T> implements FunctionOperations<T> {
    private static final Logger logger = LoggerFactory.getLogger(FunctionOperations.class);
    private final Class<T> type;
    private final Map<FunctionOperator, FunctionOperation> operations;

    public BaseFunctionOperations(Class<T> type, @NonNull Integer initialCapacity) {
        this.type = type;
        this.operations = new ConcurrentHashMap<>(initialCapacity);
    }

    public BaseFunctionOperations(Class<T> type) {
        this(type, 16);
    }

    @Override
    public void register(FunctionOperation operation) {
        if (operation != null) {
            this.operations.put(operation.operator(), operation);
//            logger.debug("==> register action: type={},operator={},action={}", type.getCanonicalName(), action.operator(), action.getClass());
        }
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public FunctionOperation get(FunctionOperator operator) {
        return operations.get(operator);
    }
}
