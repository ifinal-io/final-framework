package org.finalframework.monitor.component;

import org.finalframework.monitor.annotation.MonitorTrace;
import org.finalframework.monitor.builder.TraceOperationAnnotationBuilder;
import org.finalframework.monitor.handler.TraceOperatonHandler;
import org.finalframework.monitor.invocation.TraceInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author ilikly
 * @version 1.0
 * @date 2019-07-09 17:45
 * @since 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@OperationComponent(
        annotation = MonitorTrace.class,
        builder = TraceOperationAnnotationBuilder.class,
        handler = TraceOperatonHandler.class,
        invocation = TraceInvocation.class

)
public class TraceOperationComponent {
}
