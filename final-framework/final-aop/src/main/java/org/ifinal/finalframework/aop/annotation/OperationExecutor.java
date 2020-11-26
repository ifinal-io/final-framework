package org.ifinal.finalframework.aop.annotation;

import org.ifinal.finalframework.aop.Executor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationExecutor {
    Class<? extends Executor> value();
}
