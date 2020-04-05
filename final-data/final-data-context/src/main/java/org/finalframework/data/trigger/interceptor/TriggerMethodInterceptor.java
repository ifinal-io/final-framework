package org.finalframework.data.trigger.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.ibatis.binding.MapperProxy;
import org.finalframework.data.repository.Repository;
import org.finalframework.data.trigger.TriggerManager;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 14:52:03
 * @since 1.0
 */
@SpringComponent
public class TriggerMethodInterceptor implements MethodInterceptor, Serializable {

    private static final long serialVersionUID = -4987436811787244270L;

    private static final Logger logger = LoggerFactory.getLogger(TriggerMethodInterceptor.class);
    private static final Map<String, TriggerInterceptor> triggerInterceptors = new HashMap<>();
    private static final Map<Object, Class<? extends Repository<?, ?>>> repositories = new HashMap<>();
    private static final Field mapperInterfaceField = ReflectionUtils.findField(MapperProxy.class, "mapperInterface");

    static {
        mapperInterfaceField.setAccessible(true);
    }

    private final TriggerManager triggerManager;

    public TriggerMethodInterceptor(TriggerManager triggerManager) {
        this.triggerManager = triggerManager;
        this.init();
    }

    private void init() {
        triggerInterceptors.put("select", new SelectTriggerInterceptor(triggerManager));
        triggerInterceptors.put("selectOne", new SelectOneTriggerInterceptor(triggerManager));
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Object target = invocation.getThis();
        final Method method = invocation.getMethod();
        final Object[] arguments = invocation.getArguments();

        Class<? extends Repository<?, ?>> repository = findRepositoryClass(target);

        if (repository == null) return invocation.proceed();

        final TriggerInterceptor interceptor = triggerInterceptors.get(method.getName());
        if (interceptor != null) {
            interceptor.before(repository, method, arguments);
        }
        Object result = invocation.proceed();
        if (interceptor != null) {
            interceptor.after(repository, method, arguments, result);
        }
        return result;
    }

    private Class<? extends Repository<?, ?>> findRepositoryClass(Object target) {
        return repositories.computeIfAbsent(target, key -> {
            if (!Proxy.isProxyClass(key.getClass())) return null;
            final InvocationHandler invocationHandler = Proxy.getInvocationHandler(target);
            if (invocationHandler instanceof MapperProxy) {
                try {
                    return (Class<? extends Repository<?, ?>>) mapperInterfaceField.get(invocationHandler);
                } catch (Exception e) {
                    return null;
                }
            }

            return null;
        });


    }


}

