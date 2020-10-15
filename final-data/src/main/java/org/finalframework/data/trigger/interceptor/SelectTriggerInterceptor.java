package org.finalframework.data.trigger.interceptor;


import org.finalframework.core.Asserts;
import org.finalframework.data.query.Query;
import org.finalframework.data.repository.Repository;
import org.finalframework.data.trigger.SelectTrigger;
import org.finalframework.data.trigger.TriggerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 22:24:27
 * @see org.finalframework.data.repository.Repository#select(String, Class, Collection, Query)
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class SelectTriggerInterceptor implements TriggerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SelectTriggerInterceptor.class);

    private final TriggerManager triggerManager;

    public SelectTriggerInterceptor(TriggerManager triggerManager) {
        this.triggerManager = triggerManager;
    }

    private static Class<?> findRepositoryEntity(Class<? extends Repository<?, ?>> repository) {
        Type[] genericInterfaces = repository.getGenericInterfaces();
        for (Type type : genericInterfaces) {

            if (type instanceof ParameterizedType && Repository.class
                    .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[1];
                return (Class<?>) typeArgument;
            }

        }
        return null;
    }

    @Override
    public void before(Class<? extends Repository<?, ?>> repository, Method method, Object[] args) {
        final Class<?> entity = findRepositoryEntity(repository);
        final List<SelectTrigger<?, ?>> selectTriggers = triggerManager.getSelectTriggers(entity);
        if (Asserts.nonEmpty(selectTriggers)) {
            final String tableName = (String) args[0];
            final Class<?> view = (Class<?>) args[1];
            final Collection ids = (Collection) args[2];
            final Query query = (Query) args[3];
            selectTriggers.forEach(it -> it.beforeSelect(tableName, view, ids, query));
        }

    }

    @Override
    public void after(Class<? extends Repository<?, ?>> repository, Method method, Object[] args, Object result) {
        final Class<?> entity = findRepositoryEntity(repository);
        final List<SelectTrigger<?, ?>> selectTriggers = triggerManager.getSelectTriggers(entity);
        if (Asserts.nonEmpty(selectTriggers)) {
            final String tableName = (String) args[0];
            final Class<?> view = (Class<?>) args[1];
            final Collection ids = (Collection) args[2];
            final Query query = (Query) args[3];
            selectTriggers.forEach(it -> it.afterSelect(tableName, view, ids, query, (Collection) result));
        }
    }
}

