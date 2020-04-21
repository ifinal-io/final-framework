package org.finalframework.monitor.handler;


import java.util.Map;
import org.finalframework.core.Assert;
import org.finalframework.data.exception.IException;
import org.finalframework.monitor.MonitorException;
import org.finalframework.monitor.action.Action;
import org.finalframework.monitor.executor.Recorder;
import org.finalframework.monitor.operation.ActionOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.slf4j.MDC;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 12:20:29
 * @since 1.0
 */
@SpringComponent
public class ActionOperationHandler<T> extends AbsMonitorOperationHandlerSupport implements OperationHandler<Recorder<T>, ActionOperation> {

    @Override
    public Object before(Recorder<T> executor, OperationContext<ActionOperation> context) {
        if (CutPoint.BEFORE == context.operation().point()) {
            record(executor, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Recorder<T> executor, OperationContext<ActionOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            record(executor, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Recorder<T> executor, OperationContext<ActionOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            record(executor, context, null, throwable);
        }
    }

    @Override
    public void after(Recorder<T> executor, OperationContext<ActionOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) {
            record(executor, context, result, throwable);
        }
    }

    @SuppressWarnings("unchecked")
    private void record(Recorder<T> executor, OperationContext<ActionOperation> context, Object result, Throwable throwable) {
        final ActionOperation operation = context.operation();
        final OperationMetadata<ActionOperation> metadata = context.metadata();
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);

        final Action.Builder<T> builder = Action.builder();
        builder.name(generateName(operation.name(), metadata, evaluationContext))
            .type(operation.type())
            .action(operation.action())
            .level(operation.level())
            .operator((T) generateOperator(operation.operator(), metadata, evaluationContext))
                .target(generateTarget(operation.target(), metadata, evaluationContext))
                .trace(MDC.get("trace"))
                .timestamp(System.currentTimeMillis());


        if (throwable != null) {
            builder.exception(throwable instanceof IException
                    ? new MonitorException((IException) throwable)
                    : new MonitorException(500, throwable.getMessage()));
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
