package org.finalframework.monitor.action.annotation;

import org.finalframework.spring.aop.Invocation;
import org.finalframework.spring.aop.annotation.CutPoint;

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

    String name();

    int type() default 0;

    int action() default 0;

    String operator() default "";

    String target();

    CutPoint point() default CutPoint.AFTER_RETURNING;

    OperationAttribute[] attributes() default {};

    Class<? extends Invocation> invocation() default Invocation.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        OperationAction[] value();
    }
}
