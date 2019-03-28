package org.finalframework.monitor.action.builder;


import org.finalframework.core.Assert;
import org.finalframework.monitor.action.ActionOperation;
import org.finalframework.monitor.action.annotation.OperationAction;
import org.finalframework.monitor.action.annotation.OperationAttribute;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:06:28
 * @since 1.0
 */
public class OperationActionAnnotationBuilder implements OperationAnnotationBuilder<OperationAction, ActionOperation> {
    @Override
    public ActionOperation build(Method method, OperationAction ann) {
        final ActionOperation.Builder builder = ActionOperation.builder()
                .name(ann.name())
                .type(ann.type())
                .action(ann.action())
                .operator(ann.operator())
                .target(ann.target())
                .point(ann.point())
                .invocation(ann.invocation());

        final OperationAttribute[] attributes = ann.attributes();

        if (Assert.nonEmpty(attributes)) {
            for (OperationAttribute attribute : attributes) {
                builder.addAttribute(attribute.name(), attribute.value());
            }
        }

        return builder.build();
    }
}
