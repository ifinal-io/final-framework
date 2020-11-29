package org.ifinal.finalframework.monitor.builder;

import org.ifinal.finalframework.aop.OperationAnnotationBuilder;
import org.ifinal.finalframework.aop.annotation.OperationAttribute;
import org.ifinal.finalframework.monitor.annotation.MonitorAlert;
import org.ifinal.finalframework.monitor.operation.AlertOperation;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@SuppressWarnings("unused")
public class AlertOperationAnnotationBuilder implements OperationAnnotationBuilder<MonitorAlert, AlertOperation> {

    @Override
    @NonNull
    public AlertOperation build(@NonNull Method method, @NonNull MonitorAlert ann) {
        final String name = Asserts.isBlank(ann.name()) ? method.getDeclaringClass().getSimpleName() + "#" + method.getName() : ann.name();
        final AlertOperation.Builder builder = AlertOperation.builder()
                .name(name)
                .key(ann.key())
                .message(ann.message())
                .operator(ann.operator())
                .target(ann.target())
                .condition(ann.condition())
                .level(ann.level())
                .point(ann.point())
                .handler(ann.handler())
                .executor(ann.executor());

        final OperationAttribute[] attributes = ann.attributes();

        if (Asserts.nonEmpty(attributes)) {
            for (OperationAttribute attribute : attributes) {
                builder.addAttribute(attribute.name(), attribute.value());
            }
        }

        return builder.build();
    }
}
