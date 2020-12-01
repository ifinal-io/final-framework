package org.ifinal.finalframework.monitor.annotation;

import org.ifinal.finalframework.aop.Executor;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.AopAnnotation;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.monitor.executor.Alerter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AopAnnotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorAlert {

    String name();

    String key();

    MonitorLevel level() default MonitorLevel.INFO;

    String message() default "";

    String operator() default "";

    String target() default "";

    @AliasFor("when")
    String condition() default "";

    @AliasFor("condition")
    String when() default "";

    CutPoint point() default CutPoint.AFTER_THROWING;

    Class<? extends OperationHandler> handler() default OperationHandler.class;

    Class<? extends Executor> executor() default Alerter.class;


}
