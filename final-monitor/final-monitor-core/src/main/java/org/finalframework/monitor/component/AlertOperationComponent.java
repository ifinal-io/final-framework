package org.finalframework.monitor.component;

import org.finalframework.monitor.annotation.MonitorAlert;
import org.finalframework.monitor.builder.AlertOperationAnnotationBuilder;
import org.finalframework.monitor.handler.AlertOperationHandler;
import org.finalframework.monitor.invocation.AlertInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-11 10:30
 * @since 1.0
 */
@OperationComponent(
        annotation = MonitorAlert.class,
        builder = AlertOperationAnnotationBuilder.class,
        handler = AlertOperationHandler.class,
        invocation = AlertInvocation.class
)
public final class AlertOperationComponent {
}
