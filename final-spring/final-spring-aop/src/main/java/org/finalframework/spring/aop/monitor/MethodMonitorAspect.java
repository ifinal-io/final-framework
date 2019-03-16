package org.finalframework.spring.aop.monitor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-09 10:46
 * @since 1.0
 */
@Aspect
@Slf4j
public class MethodMonitorAspect {

    @Around(value = "@annotation(monitor)")
    public Object methodMonitor(ProceedingJoinPoint point, MethodMonitor monitor) throws Throwable {

        final long start = System.currentTimeMillis();
        Exception exception = null;
        Object result = null;
        final MethodPoint methodPoint = buildMethodPoint(point, monitor);
        try {
            MethodMonitorRegistry.getInstance().stream().forEach(it -> it.onStart(methodPoint));
            return result = point.proceed();
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            final long duration = System.currentTimeMillis() - start;
            Exception finalException = exception;
            Object finalResult = result;
            MethodMonitorRegistry.getInstance().stream()
                    .filter(listener -> listener.supports(methodPoint))
                    .forEach(listener -> {
                if (finalException != null) {
                    listener.onException(methodPoint, finalException, duration);
                } else {
                    listener.onReturn(methodPoint, finalResult, duration);
                }
                listener.onFinish(methodPoint, finalResult, finalException, duration);
            });
        }
    }

    private MethodPoint buildMethodPoint(ProceedingJoinPoint point, MethodMonitor monitor) {
        final Object target = point.getTarget();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        final Method method = methodSignature.getMethod();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = point.getArgs();

        final Map<String, Object> argMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            argMap.put(parameterNames[i], args[i]);
        }

        final String name = monitor.name();
        final String tag = monitor.tag();

        return new MethodPoint(target, method, argMap, name, tag);

    }


}
