/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.monitor.annotation;

import org.ifinalframework.core.aop.AopAnnotation;
import org.ifinalframework.core.aop.JoinPoint;
import org.ifinalframework.core.lang.SpEL;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <pre class="code">
 * public class UserService{
 *
 *      &#64;OperationAction(name="Update password")
 *      void updatePassword(String oldPwd,String newPwd);
 *
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.2.2
 * @since 1.2.2
 */
@Documented
@AopAnnotation(expressions = {"name", "target"})
@Repeatable(OperationAction.OperationActions.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationAction {

    /**
     * action name
     *
     * @see #value()
     */
    @SpEL
    @AliasFor("value")
    String[] name() default {};

    /**
     * action name
     *
     * @see #name()
     */
    @SpEL
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
    @SpEL
    String target() default "";

    /**
     * @return
     * @since 1.2.2
     */
    Attribute[] attributes() default {};

    /**
     * 级别
     */
    MonitorLevel level() default MonitorLevel.INFO;

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

    /**
     * @since 1.2.2
     */
    @interface Attribute {
        /**
         * return attribute name
         */
        String name();

        /**
         * return attribute value, supports {@code SpEL}.
         */
        @SpEL
        String value();
    }

    /**
     * ActionMonitors.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface OperationActions {

        OperationAction[] value();

    }

}
