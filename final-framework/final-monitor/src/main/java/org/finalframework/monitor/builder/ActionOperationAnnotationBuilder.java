package org.finalframework.monitor.builder;


import org.finalframework.aop.OperationAnnotationBuilder;
import org.finalframework.aop.annotation.OperationAttribute;
import org.finalframework.monitor.annotation.MonitorAction;
import org.finalframework.monitor.operation.ActionOperation;
import org.finalframework.util.Asserts;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:06:28
 * @since 1.0
 */
@Component
@SuppressWarnings("unused")
public class ActionOperationAnnotationBuilder implements OperationAnnotationBuilder<MonitorAction, ActionOperation> {
    @Override
    public ActionOperation build(Method method, MonitorAction ann) {
        final String name = Asserts.isBlank(ann.name()) ? method.getDeclaringClass().getSimpleName() + "#" + method.getName() : ann.name();
        final ActionOperation.Builder builder = ActionOperation.builder()
                .name(name)
                .type(ann.type())
                .action(ann.action())
                .operator(ann.operator())
                .level(ann.level())
                .target(ann.target())
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
