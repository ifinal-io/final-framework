package org.finalframework.monitor.action.invocation;


import org.finalframework.core.Assert;
import org.finalframework.data.exception.IException;
import org.finalframework.monitor.action.*;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.slf4j.MDC;
import org.springframework.expression.EvaluationContext;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 12:20:29
 * @since 1.0
 */
public class ActionOperationOperationHandler<T> extends AbsActionInvocationSupport implements ActionOperationHandler<T> {

    @Override
    public Object before(ActionRecorder<T> executor, OperationContext<ActionOperation> context, Object result) {
        if (CutPoint.BEFORE == context.operation().point()) {
            record(executor, context, result, null);
        }
        return null;
    }

    @Override
    public void afterReturning(ActionRecorder<T> executor, OperationContext<ActionOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            record(executor, context, result, null);
        }
    }

    @Override
    public void afterThrowing(ActionRecorder<T> executor, OperationContext<ActionOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            record(executor, context, null, throwable);
        }
    }

    @Override
    public void after(ActionRecorder<T> executor, OperationContext<ActionOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) {
            record(executor, context, result, throwable);
        }
    }

    @SuppressWarnings("unchecked")
    private void record(ActionRecorder<T> executor, OperationContext<ActionOperation> context, Object result, Throwable throwable) {
        final ActionOperation operation = context.operation();
        final OperationMetadata<ActionOperation> metadata = context.metadata();
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);

        final ActionContext.Builder<T> builder = ActionContext.builder();
        builder.name(operation.name())
                .type(operation.type())
                .action(operation.action())
                .level(operation.level())
                .operator(generateOperator(operation.operator(), metadata, evaluationContext))
                .target(generateTarget(operation.target(), metadata, evaluationContext))
                .trace(MDC.get("trace"))
                .timestamp(System.currentTimeMillis());


        if (throwable != null) {
            builder.exception(throwable instanceof IException
                    ? new ActionException((IException) throwable)
                    : new ActionException(500, throwable.getMessage()));
        }


        final Map<String, String> attributes = operation.attributes();
        if (Assert.nonEmpty(attributes)) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                builder.addAttribute(entry.getKey(), generateAttribute(entry.getValue(), metadata, evaluationContext));
            }
        }

        executor.record(builder.build());

    }


}
