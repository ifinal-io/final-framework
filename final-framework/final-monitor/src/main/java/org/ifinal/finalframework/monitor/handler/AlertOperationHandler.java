package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.OperationMetadata;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.monitor.MonitorException;
import org.ifinal.finalframework.monitor.context.AlertContext;
import org.ifinal.finalframework.monitor.executor.Alerter;
import org.ifinal.finalframework.monitor.operation.AlertOperation;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.MDC;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AlertOperationHandler<T> extends AbsMonitorOperationHandlerSupport implements OperationHandler<Alerter, AlertOperation> {

    @Override
    public Object before(Alerter executor, OperationContext<AlertOperation> context) {
        if (CutPoint.BEFORE == context.operation().point()) {
            alert(executor, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Alerter executor, OperationContext<AlertOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            alert(executor, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Alerter executor, OperationContext<AlertOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            alert(executor, context, null, throwable);
        }
    }

    @Override
    public void after(Alerter executor, OperationContext<AlertOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) {
            alert(executor, context, result, throwable);
        }
    }

    private void alert(Alerter executor, OperationContext<AlertOperation> context, Object result, Throwable throwable) {
        final AlertOperation operation = context.operation();
        final OperationMetadata<AlertOperation> metadata = context.metadata();
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);

        final AlertContext.Builder<T> builder = AlertContext.builder();
        builder.name(operation.name())
                .level(operation.level())
                .operator(generateOperator(operation.operator(), metadata, evaluationContext))
                .trace(MDC.get("trace"))
                .timestamp(System.currentTimeMillis());


        if (throwable != null) {
            builder.exception(throwable instanceof IException
                    ? new MonitorException((IException) throwable)
                    : new MonitorException(500, throwable.getMessage()));
        }


        final Map<String, String> attributes = operation.attributes();
        if (Asserts.nonEmpty(attributes)) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                builder.addAttribute(entry.getKey(), generateAttribute(entry.getValue(), metadata, evaluationContext));
            }
        }

        executor.alert(builder.build());
    }
}
