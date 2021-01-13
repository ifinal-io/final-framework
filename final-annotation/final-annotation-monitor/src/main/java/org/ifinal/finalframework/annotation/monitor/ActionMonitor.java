package org.ifinal.finalframework.annotation.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.aop.AopAnnotation;
import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.1.2-SNAPSHOT
 * @since 1.1.2-SNAPSHOT
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

    /**
     * ActionMonitors.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface ActionMonitors {

        ActionMonitor[] value();

    }

}
