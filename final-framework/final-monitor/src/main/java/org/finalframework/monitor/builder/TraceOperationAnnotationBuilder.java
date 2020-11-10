package org.finalframework.monitor.builder;

import org.finalframework.aop.OperationAnnotationBuilder;
import org.finalframework.monitor.annotation.MonitorTrace;
import org.finalframework.monitor.operation.TraceOperation;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 17:38
 * @since 1.0
 */
@Component
@SuppressWarnings("unused")
public class TraceOperationAnnotationBuilder implements OperationAnnotationBuilder<MonitorTrace, TraceOperation> {

    @Override
    public TraceOperation build(Class<?> type, MonitorTrace ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public TraceOperation build(Method method, MonitorTrace ann) {
        return build((AnnotatedElement) method, ann);
    }

    private TraceOperation build(AnnotatedElement ae, MonitorTrace ann) {
        return TraceOperation.builder()
                .name(ae.toString())
                .trace(ann.value())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();
    }
}
