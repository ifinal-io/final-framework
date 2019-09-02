package org.finalframework.spring.aop.annotation;

import org.finalframework.spring.aop.Executor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 15:30
 * @since 1.0
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationExecutor {
    Class<? extends Executor> value();
}
