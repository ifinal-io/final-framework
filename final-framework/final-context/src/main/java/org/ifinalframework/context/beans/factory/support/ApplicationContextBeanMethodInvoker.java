package org.ifinalframework.context.beans.factory.support;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import org.ifinalframework.json.Json;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

import lombok.Setter;

/**
 * @author iimik
 * @version 1.2.4
 **/
public class ApplicationContextBeanMethodInvoker implements BeanMethodInvoker, ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public Object invoke(@NonNull String beanName, @NonNull String methodName, Class<?>[] parameterTypes, Object[] args) {
        Object bean = applicationContext.getBean(beanName);
        return invoke(bean, methodName, parameterTypes, args);
    }

    @Override
    public Object invoke(@NonNull Class<?> beanType, @NonNull String methodName, Class<?>[] parameterTypes, Object[] args) {
        Object bean = applicationContext.getBean(beanType);
        Method method = findMethod(beanType, methodName, parameterTypes);
        return doInvoke(bean, method, args);
    }

    @Override
    public Object invoke(@NonNull Object bean, @NonNull String methodName, Class<?>[] parameterTypes, Object[] args) {
        final Class<?> targetClass = AopUtils.getTargetClass(bean);
        final Method method = findMethod(targetClass, methodName, parameterTypes);
        return doInvoke(bean, method, args);
    }

    private Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        if (Objects.isNull(parameterTypes)) {
            return ReflectionUtils.findMethod(clazz, methodName);
        }

        return ReflectionUtils.findMethod(clazz, methodName, parameterTypes);
    }

    private Object doInvoke(Object bean, Method method, Object[] args) {

        ReflectionUtils.makeAccessible(method);

        if (Objects.isNull(args)) {
            return ReflectionUtils.invokeMethod(method, bean);
        }

        Object[] arguments = buildArguments(method, args);

        return ReflectionUtils.invokeMethod(method, bean, arguments);
    }

    private Object[] buildArguments(Method method, Object[] args) {
        final Type[] types = method.getGenericParameterTypes();
        final Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof String) {
                arguments[i] = Json.toObject((String) arg, types[i]);
            } else {
                arguments[i] = arg;
            }
        }
        return arguments;
    }
}
