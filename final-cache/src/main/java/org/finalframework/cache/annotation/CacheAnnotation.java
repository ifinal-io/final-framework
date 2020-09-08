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

import org.finalframework.spring.aop.annotation.CutPoint;

import java.lang.annotation.*;

/**
 * Cache 标记注释，只是用于标记 {@link Annotation} 是缓存注释。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-02 21:55:29
 * @see Cacheable
 * @see CacheLock
 * @see CacheIncrement
 * @see CachePut
 * @see CacheValue
 * @see CacheDel
 * @since 1.0
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface CacheAnnotation {
    /**
     * 仅描述该缓存 {@link Annotation} 的调用时机。
     */
    CutPoint[] value();
}
