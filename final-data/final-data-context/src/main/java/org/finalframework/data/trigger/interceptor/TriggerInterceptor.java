package org.finalframework.data.trigger.interceptor;

import org.finalframework.data.repository.Repository;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 22:15:50
 * @since 1.0
 */
public interface TriggerInterceptor {

    void before(Class<? extends Repository<?, ?>> repository, Method method, Object[] args);


    void after(Class<? extends Repository<?, ?>> repository, Method method, Object[] args, Object result);

}
