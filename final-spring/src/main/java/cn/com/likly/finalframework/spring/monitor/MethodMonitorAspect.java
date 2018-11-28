package cn.com.likly.finalframework.spring.monitor;

import cn.com.likly.finalframework.spring.annotation.MethodMonitor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-09 10:46
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
public class MethodMonitorAspect {

    private final List<MethodMonitorListener> methodMonitorListeners = new CopyOnWriteArrayList<>();

    public void registerMethodMonitorListener(@NonNull MethodMonitorListener listener) {
        this.methodMonitorListeners.add(listener);
    }

    public void unRegisterMethodMonitorListener(@NonNull MethodMonitorListener listener) {
        this.methodMonitorListeners.remove(listener);
    }


    @Around(value = "@annotation(monitor)")
    public Object methodMonitor(ProceedingJoinPoint point, MethodMonitor monitor) throws Throwable {

        final long start = System.currentTimeMillis();
        Exception exception = null;
        Object result = null;
        final MethodPoint methodPoint = buildMethodPoint(point, monitor);
        try {
            methodMonitorListeners.forEach(it -> it.onStart(methodPoint));
            return result = point.proceed();
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            final long duration = System.currentTimeMillis() - start;

            for (MethodMonitorListener listener : methodMonitorListeners) {

                if (exception != null) {
                    listener.onException(methodPoint, exception, duration);
                } else {
                    listener.onReturn(methodPoint, result, duration);
                }

                listener.onFinish(methodPoint, result, exception, duration);
            }

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
