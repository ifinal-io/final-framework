package org.ifinal.finalframework.monitor;

import org.ifinal.finalframework.aop.Operation;
import org.ifinal.finalframework.aop.OperationHandlerSupport;
import org.ifinal.finalframework.aop.OperationMetadata;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MonitorOperationHandlerSupport extends OperationHandlerSupport {

    @Nullable
    String generateName(@NonNull String name, @NonNull OperationMetadata<? extends Operation> metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateOperator(@NonNull String operator, @NonNull OperationMetadata<? extends Operation> metadata, @NonNull EvaluationContext evaluationContext);

    Object generateTarget(@NonNull String target, @NonNull OperationMetadata<? extends Operation> metadata, @NonNull EvaluationContext evaluationContext);

    Object generateAttribute(@NonNull String attribute, @NonNull OperationMetadata<? extends Operation> metadata, @NonNull EvaluationContext evaluationContext);
}
