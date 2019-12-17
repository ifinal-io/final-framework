package org.finalframework.data.query.criterion.operation;


import org.finalframework.data.query.criterion.CriterionOperation;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.CriterionOperations;
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

public class BaseCriterionOperations<T> implements CriterionOperations<T> {
    private static final Logger logger = LoggerFactory.getLogger(CriterionOperations.class);
    private final Class<T> type;
    private final Map<CriterionOperator, CriterionOperation> operations;

    public BaseCriterionOperations(Class<T> type, @NonNull Integer initialCapacity) {
        this.type = type;
        this.operations = new ConcurrentHashMap<>(initialCapacity);
    }

    public BaseCriterionOperations(Class<T> type) {
        this(type, 16);
    }

    @Override
    public void register(CriterionOperation operation) {
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
    public CriterionOperation get(CriterionOperator operator) {
        return operations.get(operator);
    }
}
