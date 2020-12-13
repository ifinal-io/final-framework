package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.annotation.monitor.ActionMonitor;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.JoinPointInterceptorHandler;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.monitor.action.Action;
import org.ifinal.finalframework.monitor.action.Recorder;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @see ActionMonitor
 * @since 1.0.0
 */
public class ActionInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport implements
    JoinPointInterceptorHandler<Recorder, AnnotationAttributes> {

    /**
     * @see ActionMonitor#name()
     * @see ActionMonitor#value()
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * @see ActionMonitor#target()
     */
    private static final String ATTRIBUTE_TARGET = "target";

    /**
     * @see ActionMonitor#code()
     */
    private static final String ATTRIBUTE_CODE = "code";

    /**
     * @see ActionMonitor#type()
     */
    private static final String ATTRIBUTE_TYPE = "type";

    /**
     * @see ActionMonitor#level()
     */
    private static final String ATTRIBUTE_LEVEL = "level";

    /**
     * @see ActionMonitor#point()
     */
    private static final String ATTRIBUTE_POINT = "point";

    /**
     * @param annotation annotation
     * @return the action join point
     * @see ActionMonitor#point()
     */
    @Override
    public JoinPoint point(final AnnotationAttributes annotation) {

        return annotation.getEnum(ATTRIBUTE_POINT);
    }

    @Override
    public void handle(final @NonNull Recorder executor, final @NonNull InvocationContext context,
        final @NonNull AnnotationAttributes annotation,
        final Object result, final Throwable throwable) {

        EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        MethodMetadata metadata = context.metadata();

        final Action action = new Action();
        action.setName(generateName(annotation.getStringArray(ATTRIBUTE_NAME), ":", metadata, evaluationContext));
        action.setTarget(generateTarget(annotation.getString(ATTRIBUTE_TARGET), metadata, evaluationContext));
        action.setCode(annotation.getString(ATTRIBUTE_CODE));
        action.setType(annotation.getString(ATTRIBUTE_TYPE));
        action.setLevel(annotation.getEnum(ATTRIBUTE_LEVEL));
        action.setException(throwable);
        action.setTrace(MDC.get("trace"));
        action.setTimestamp(System.currentTimeMillis());

        executor.record(action);
    }

}
