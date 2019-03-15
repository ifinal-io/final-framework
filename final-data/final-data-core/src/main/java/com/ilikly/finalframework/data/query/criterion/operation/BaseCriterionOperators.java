package com.ilikly.finalframework.data.query.criterion.operation;


import com.ilikly.finalframework.data.query.CriterionOperation;
import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.criterion.CriterionOperators;
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

public class BaseCriterionOperators<T> implements CriterionOperators<T> {
    private static final Logger logger = LoggerFactory.getLogger(CriterionOperators.class);
    private final Class<T> type;
    private final Map<CriterionOperator, CriterionOperation> operations;

    public BaseCriterionOperators(Class<T> type, @NonNull Integer initialCapacity) {
        this.type = type;
        this.operations = new ConcurrentHashMap<>(initialCapacity);
    }

    public BaseCriterionOperators(Class<T> type) {
        this(type, 16);
    }

    @Override
    public void register(CriterionOperation operation) {
        if (operation != null) {
            this.operations.put(operation.operator(), operation);
            logger.debug("==> register operation: type={},operator={},operation={}", type.getCanonicalName(), operation.operator(), operation.getClass());
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
