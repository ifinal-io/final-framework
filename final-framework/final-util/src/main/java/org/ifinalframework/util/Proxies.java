/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.util;


import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理工具类
 *
 * @author iimik
 * @since 1.6.0
 **/
@Slf4j
@UtilityClass
public class Proxies {

    private static final CompositeProxyFactory compositeProxyFactory = new JdkCompositeProxyFactory();


    /**
     * mapper proxy.
     *
     * @see org.apache.ibatis.binding.MapperProxy
     */
    public static final String MAPPER_PROXY = "org.apache.ibatis.binding.MapperProxy";

    /**
     * jdk dynamic aop proxy.
     */
    public static final String JDK_DYNAMIC_AOP_PROXY = "org.springframework.aop.framework.JdkDynamicAopProxy";


    /**
     * 判断目标 {@code target} 是否为代理.
     *
     * @param target target
     * @return result
     * @see Proxy#isProxyClass(Class)
     * @see AopUtils#isAopProxy(Object)
     */
    public static boolean isProxy(final Object target) {
        return Proxy.isProxyClass(target.getClass()) || AopUtils.isAopProxy(target);
    }

    /**
     * return the real target.
     *
     * @param target target
     * @return result
     */
    public static Object target(final Object target) {

        while (isProxy(target)) {
            if (Proxy.isProxyClass(target.getClass())) {
                return target(Proxy.getInvocationHandler(target));
            }
            if (AopUtils.isAopProxy(target)) {
                return target(AopProxyUtils.ultimateTargetClass(target));
            }
        }

        if (JDK_DYNAMIC_AOP_PROXY.equals(target.getClass().getCanonicalName())) {
            try {
                final Field advisedField = ReflectionUtils.findField(target.getClass(), "advised");
                Objects.requireNonNull(advisedField, "");
                ReflectionUtils.makeAccessible(advisedField);
                final Object advised = ReflectionUtils.getField(advisedField, target);
                Objects.requireNonNull(advised, "");
                final Field targetSourceField = ReflectionUtils.findField(advised.getClass(), "targetSource");
                Objects.requireNonNull(targetSourceField, "");
                ReflectionUtils.makeAccessible(targetSourceField);
                final TargetSource targetSource = (TargetSource) ReflectionUtils.getField(targetSourceField, advised);
                Objects.requireNonNull(targetSource, "");
                return target(targetSource.getTarget());
            } catch (Exception e) {
                logger.error("parse JDK AOP PROXY target error: {}", target.getClass(), e);
            }
        }

        return target;
    }

    /**
     * Returns target class of proxy.
     */
    public static Class<?> targetClass(final Object proxy) {

        final Object target = target(proxy);
        final Class<?> targetClass = target.getClass();
        if (MAPPER_PROXY.equals(targetClass.getCanonicalName())) {
            try {
                final Field mapperInterface = ReflectionUtils.findField(targetClass, "mapperInterface");
                Objects.requireNonNull(mapperInterface, "");
                ReflectionUtils.makeAccessible(mapperInterface);
                return (Class<?>) ReflectionUtils.getField(mapperInterface, target);
            } catch (Exception e) {
                // ignore
            }
        }
        return targetClass;
    }


    /**
     * 创建给定类和实例集合的复合代理对象
     *
     * @param clazz 给定类
     * @param list  实例集合
     * @param <T>   类类型
     * @return 复合代理对象
     * @since 1.6.0
     */
    public static <T> T composite(Class<T> clazz, List<T> list) {
        return compositeProxyFactory.create(clazz, list);
    }

    /**
     * 复合代理工厂
     *
     * @since 1.6.0
     */
    @FunctionalInterface
    public interface CompositeProxyFactory {
        /**
         * 根据给定的{@link Class 类}和实例集合创建复合代理对象。
         *
         * @param clazz 给定的类
         * @param list  实例集合
         * @param <T>   类类型
         * @return 复合代理对象
         */
        <T> T create(Class<T> clazz, List<T> list);
    }
}
