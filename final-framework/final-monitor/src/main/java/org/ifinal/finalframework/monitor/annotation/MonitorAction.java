package org.ifinal.finalframework.monitor.annotation;

import org.ifinal.finalframework.aop.Executor;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.AopAnnotation;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.monitor.executor.Recorder;
import org.ifinal.finalframework.monitor.handler.ActionOperationHandler;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.monitor.action.Action
 * @see org.ifinal.finalframework.monitor.action.ActionListener
 * @since 1.0.0
 */

@Documented
@AopAnnotation(expressions = {"name", "target"})
@Repeatable(MonitorAction.List.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorAction {

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
     * 操作目标
     */
    String target() default "";

    /**
     * 级别
     */
    MonitorLevel level() default MonitorLevel.INFO;

    CutPoint point() default CutPoint.AFTER_RETURNING;

    Class<? extends OperationHandler> handler() default ActionOperationHandler.class;

    Class<? extends Executor> executor() default Recorder.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        MonitorAction[] value();
    }
}
