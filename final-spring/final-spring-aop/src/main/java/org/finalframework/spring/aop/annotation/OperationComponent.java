package org.finalframework.spring.aop.annotation;

import org.finalframework.spring.aop.Invocation;
import org.finalframework.spring.aop.OperationAnnotationBuilder;
import org.finalframework.spring.aop.OperationHandler;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 15:10
 * @since 1.0
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationComponent {
    Class<? extends Annotation> annotation();

    Class<? extends OperationAnnotationBuilder> builder();

    Class<? extends OperationHandler> handler();

    Class<? extends Invocation> invocation();
}
