package org.finalframework.monitor.annotation;

import org.finalframework.monitor.executor.Alerter;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.AdviceAnnotation;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.finalframework.spring.aop.annotation.OperationAttribute;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-10 10:33
 * @since 1.0
 */
@AdviceAnnotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorAlert {

    String name();

    String key();

    MonitorLevel level() default MonitorLevel.INFO;

    String message() default "";

    OperationAttribute[] attributes() default {};

    String operator() default "";

    String target() default "";

    @AliasFor("when")
    String condition() default "";

    @AliasFor("condition")
    String when() default "";

    CutPoint point() default CutPoint.AFTER_RETURNING;

    Class<? extends OperationHandler> handler() default OperationHandler.class;

    Class<? extends Executor> executor() default Alerter.class;


}
