package org.ifinal.finalframework.annotation.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.aop.AopAnnotation;
import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AopAnnotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlertMonitor {

    String[] name();

    String[] key();

    MonitorLevel level() default MonitorLevel.INFO;

    String[] message() default "";

    String[] target() default "";

    @AliasFor("when")
    String condition() default "";

    @AliasFor("condition")
    String when() default "";

    JoinPoint point() default JoinPoint.AFTER_THROWING;

}
