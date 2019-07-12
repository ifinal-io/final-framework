package org.finalframework.monitor.builder;

import org.finalframework.monitor.annotation.MonitorTrace;
import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author ilikly
 * @version 1.0
 * @date 2019-07-09 17:38
 * @since 1.0
 */
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
