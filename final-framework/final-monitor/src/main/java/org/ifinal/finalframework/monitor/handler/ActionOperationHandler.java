package org.ifinal.finalframework.monitor.handler;


import org.ifinal.finalframework.annotation.IUser;
import org.ifinal.finalframework.aop.AnnotationInvocationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.monitor.annotation.MonitorAction;
import org.ifinal.finalframework.monitor.executor.Recorder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ActionOperationHandler<T extends IUser> extends AbsMonitorOperationHandlerSupport implements OperationHandler<Recorder<T>, MonitorAction> {

    @Override
    public Object before(Recorder<T> executor, AnnotationInvocationContext context) {
        if (CutPoint.BEFORE == context.cutPoint()) {
            record(executor, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Recorder<T> executor, AnnotationInvocationContext context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.cutPoint()) {
            record(executor, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Recorder<T> executor, AnnotationInvocationContext context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.cutPoint()) {
            record(executor, context, null, throwable);
        }
    }

    @Override
    public void after(Recorder<T> executor, AnnotationInvocationContext context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.cutPoint()) {
            record(executor, context, result, throwable);
        }
    }

    @SuppressWarnings("unchecked")
    private void record(Recorder<T> executor, AnnotationInvocationContext context, Object result, Throwable throwable) {
        final AnnotationAttributes operation = context.annotationAttributes();
        final MethodMetadata metadata = context.metadata();
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);

    }


}
