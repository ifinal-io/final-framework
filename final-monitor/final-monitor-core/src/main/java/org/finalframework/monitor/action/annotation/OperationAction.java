package org.finalframework.monitor.action.annotation;

import org.finalframework.monitor.action.ActionLevel;
import org.finalframework.monitor.action.interceptor.DefaultActionRecorder;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.Invocation;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 22:56:04
 * @since 1.0
 */

@Documented
@Repeatable(OperationAction.List.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationAction {

    /**
     * 操作名称
     *
     * @see #value()
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 操作名称
     *
     * @see #name()
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 操作类型
     */
    int type() default 0;

    /**
     * 操作动作
     */
    int action() default 0;

    /**
     * 操作者
     */
    String operator() default "";

    /**
     * 操作目标
     */
    String target() default "";

    /**
     * 级别
     */
    ActionLevel level() default ActionLevel.INFO;

    CutPoint point() default CutPoint.AFTER_RETURNING;

    OperationAttribute[] attributes() default {};

    Class<? extends Invocation> invocation() default Invocation.class;

    Class<? extends Executor> executor() default DefaultActionRecorder.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        OperationAction[] value();
    }
}
