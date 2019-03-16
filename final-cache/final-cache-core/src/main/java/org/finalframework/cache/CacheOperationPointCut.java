package org.finalframework.cache;

import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/**
 * 缓存切点
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-10 22:36:00
 * @since 1.0
 */
public interface CacheOperationPointCut {
    /**
     * 返回目标方法 {@link Method} 和 目标类 {@link Class} 是否匹配缓存切点，如果匹配则返回 {@literal true}，否则返回 {@literal false}。
     *
     * @param method      目标方法
     * @param targetClass 目标方法所属的类
     * @return 如果匹配返回 {@literal true}，否则返回 {@literal false}。
     */
    boolean matches(@NonNull Method method, @NonNull Class<?> targetClass);
}