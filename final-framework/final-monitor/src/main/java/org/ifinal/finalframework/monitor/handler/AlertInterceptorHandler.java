package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.monitor.MonitorException;
import org.ifinal.finalframework.monitor.context.AlertContext;
import org.ifinal.finalframework.monitor.executor.Alerter;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AlertInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport<Alerter> {

    @Override
    protected void doHandle(@NonNull Alerter executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, Object result, Throwable throwable) {

        final AlertContext.Builder builder = AlertContext.builder();
        builder.name(annotation.getString("name"))
                .level(level(annotation))
                .trace(MDC.get("trace"))
                .timestamp(System.currentTimeMillis());


        if (throwable != null) {
            builder.exception(throwable instanceof IException
                    ? new MonitorException(500, throwable.getMessage(), (IException) throwable)
                    : new MonitorException(500, throwable.getMessage()));
        }

        executor.alert(builder.build());
    }
}
