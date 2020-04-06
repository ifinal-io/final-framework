package org.finalframework.data.service;

import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-24 11:14:03
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface AbsService<ID extends Serializable, T extends IEntity<ID>, R extends Repository<ID, T>> extends Repository<ID, T> {
    @Override
    default int save(String tableName, Class<?> view, Collection<T> entities) {
        return getRepository().save(tableName, view, entities);
    }

    @Override
    default int insert(String tableName, Class<?> view, boolean ignore, Collection<T> entities) {
        return getRepository().insert(tableName, view, ignore, entities);
    }

    @Override
    default int replace(String tableName, Class<?> view, Collection<T> entities) {
        return getRepository().replace(tableName, view, entities);
    }

    @Override
    default int update(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query) {
        return getRepository().update(tableName, view, entity, update, selective, ids, query);
    }

    @Override
    default int delete(String tableName, Collection<ID> ids, Query query) {
        return getRepository().delete(tableName, ids, query);
    }

    @Override
    default List<T> select(String tableName, Class<?> view, Collection<ID> ids, Query query) {
        return getRepository().select(tableName, view, ids, query);
    }

    @Override
    default T selectOne(String tableName, Class<?> view, ID id, Query query) {
        return getRepository().selectOne(tableName, view, id, query);
    }

    @Override
    default List<ID> selectIds(String tableName, Query query) {
        return getRepository().selectIds(tableName, query);
    }


    @Override
    default long selectCount(String tableName, Collection<ID> ids, Query query) {
        return getRepository().selectCount(tableName, ids, query);
    }

    @Override
    default void truncate(String tableName) {
        getRepository().truncate(tableName);
    }

    @NonNull
    R getRepository();

}
