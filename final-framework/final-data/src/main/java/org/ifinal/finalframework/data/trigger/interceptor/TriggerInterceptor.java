package org.ifinal.finalframework.data.trigger.interceptor;

import org.ifinal.finalframework.data.repository.Repository;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface TriggerInterceptor {

    void before(Class<? extends Repository<?, ?>> repository, Method method, Object[] args);


    void after(Class<? extends Repository<?, ?>> repository, Method method, Object[] args, Object result);

}
