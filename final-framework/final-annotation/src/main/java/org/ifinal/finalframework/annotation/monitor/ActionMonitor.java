package org.ifinal.finalframework.annotation.monitor;

import org.ifinal.finalframework.annotation.aop.AopAnnotation;
import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.2
 * @since 1.0.2
 */

@Documented
@AopAnnotation(expressions = {"name", "target"})
@Repeatable(ActionMonitor.ActionMonitors.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionMonitor {

    /**
     * action name
     *
     * @see #value()
     */
    @AliasFor("value")
    String[] name() default {};

    /**
     * action name
     *
     * @see #name()
     */
    @AliasFor("name")
    String[] value() default {};

    /**
     * 操作类型
     */
    String type() default "";

    /**
     * 操作动作
     */
    String code() default "code";


    /**
     * 操作目标
     */
    String target() default "";

    /**
     * 级别
     */
    MonitorLevel level() default MonitorLevel.INFO;

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface ActionMonitors {
        ActionMonitor[] value();
    }
}
