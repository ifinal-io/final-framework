

package org.finalframework.util;


import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-06 16:53:20
 * @since 1.0
 */
public abstract class ProxyUtils {

    /**
     * @see org.apache.ibatis.binding.MapperProxy
     */
    public static final String MAPPER_PROXY = "org.apache.ibatis.binding.MapperProxy";
    /**
     * @see org.springframework.aop.framework.JdkDynamicAopProxy
     */
    public static final String JDK_DYNAMIC_AOP_PROXY = "org.springframework.aop.framework.JdkDynamicAopProxy";

    /**
     * 判断目标 {@code target} 是否为代理
     *
     * @param target
     * @return
     * @see Proxy#isProxyClass(Class)
     * @see AopUtils#isAopProxy(Object)
     */
    public static boolean isProxy(Object target) {
        return Proxy.isProxyClass(target.getClass()) || AopUtils.isAopProxy(target);
    }

    /**
     * @param target
     * @return
     * @throws Throwable
     */
    public static Object target(Object target) {
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
                advisedField.setAccessible(true);
                Object advised = advisedField.get(target);
                final Field targetSourceField = ReflectionUtils.findField(advised.getClass(), "targetSource");
                targetSourceField.setAccessible(true);
                final TargetSource targetSource = (TargetSource) targetSourceField.get(advised);
                return target(targetSource.getTarget());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return target;
    }

    public static Class<?> targetClass(Object proxy) {
        Object target = target(proxy);
        final Class<?> targetClass = target.getClass();
        if (MAPPER_PROXY.equals(targetClass.getCanonicalName())) {
            try {
                final Field mapperInterface = ReflectionUtils.findField(targetClass, "mapperInterface");
                mapperInterface.setAccessible(true);
                return (Class<?>) mapperInterface.get(target);
            } catch (Exception e) {
                // ignore
            }
        }
        return targetClass;
    }


}

