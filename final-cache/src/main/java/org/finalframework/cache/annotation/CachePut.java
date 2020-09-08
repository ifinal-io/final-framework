/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.cache.annotation;


import org.finalframework.cache.Cache;
import org.finalframework.cache.handler.CachePutOperationHandler;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CachePut.List.class)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER, CutPoint.AFTER_RETURNING, CutPoint.AFTER_THROWING})
public @interface CachePut {

    String[] key();

    String[] field() default {};

    String value() default "";

    String delimiter() default ":";

    CutPoint point() default CutPoint.AFTER_RETURNING;

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    Class<? extends OperationHandler> handler() default CachePutOperationHandler.class;

    Class<? extends Cache> executor() default Cache.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CachePut[] value();
    }
}
