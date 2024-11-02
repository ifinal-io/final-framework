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

package org.ifinalframework.cache.annotation;

import org.ifinalframework.core.lang.SpEL;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * 缓存，为目标方法增加缓存能力。
 * <ol>
 * <li>在执行之前，优先从 {@link #key()} 和 {@link #field()} 所描述的缓存区域里读取
 * {@link Cache#get(Object, Object, Type, Class)}，如果命中，则直接返回。</li>
 * <li>在执行之后，将方法的执行结果写到 {@link #key()} 和 {@link #field()} 所描述的缓存区域里，
 * {@link Cache#set(Object, Object, Object, Long, TimeUnit, Class)}。</li>
 * </ol>
 *
 * <h3>Usage:</h3>
 * <p>Normal, use cache like this: </p>
 * <pre class="code">
 * public User findUserById(Long id){
 *     User user = Cache.get(id,null);
 *     if(Objects.nonNull(user)){
 *         return user;
 *     }
 *
 *     user = doFindUserById(id);
 *
 *     if(Objects.nonNull(user)){
 *         Cache.set(id,null,user);
 *     }
 *
 *     return user;
 * }
 * </pre>
 *
 * <p>Now, use cache with @Cacheable like this:</p>
 * <pre class="code">
 *     &#64;Cacheable(key="#{#id}")
 *     public User findUserById(Long id){
 *         ...
 *     }
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @see Cache#get(Object, Object, Type, Class)
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    /**
     * 缓存区
     *
     * @return key
     */
    @SpEL
    String[] key();

    /**
     * 缓存域
     *
     * @return field
     */
    @SpEL
    String[] field() default {};

    /**
     * 缓存区域的分隔符
     *
     * @return delimiter
     */
    String delimiter() default ":";

    /**
     * 缓存条件
     *
     * @return condition
     * @see #when()
     */
    @SpEL
    @AliasFor("when")
    String condition() default "";

    /**
     * 缓存条件
     *
     * @return when
     * @see #condition()
     */
    @SpEL
    @AliasFor("condition")
    String when() default "";

    /**
     * 过期时间
     *
     * @return expire
     */
    @SpEL
    String expire() default "";

    /**
     * 有效时间
     *
     * @return ttl
     */
    long ttl() default -1L;

    /**
     * 有效时间单位
     *
     * @return timeunit
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

}
