package org.finalframework.monitor.builder;


import org.finalframework.core.Assert;
import org.finalframework.monitor.annotation.OperationAction;
import org.finalframework.monitor.operation.ActionOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationAnnotationBuilder;
import org.finalframework.spring.aop.annotation.OperationAttribute;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:06:28
 * @since 1.0
 */
@SpringComponent
public class ActionOperationAnnotationBuilder implements OperationAnnotationBuilder<OperationAction, ActionOperation> {
    @Override
    public ActionOperation build(Method method, OperationAction ann) {
        final String name = Assert.isBlank(ann.name()) ? method.getDeclaringClass().getSimpleName() + "#" + method.getName() : ann.name();
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

        if (Assert.nonEmpty(attributes)) {
            for (OperationAttribute attribute : attributes) {
                builder.addAttribute(attribute.name(), attribute.value());
            }
        }

        return builder.build();
    }
}
