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
 * @author iimik
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

    @SpEL
    @AliasFor("when")
    String condition() default "";

    @SpEL
    @AliasFor("condition")
    String when() default "";

    JoinPoint point() default JoinPoint.AFTER_THROWING;

}
