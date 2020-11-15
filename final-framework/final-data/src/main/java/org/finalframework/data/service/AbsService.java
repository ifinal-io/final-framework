package org.finalframework.data.service;

import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.data.query.Update;
import org.finalframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-24 11:14:03
 * @since 1.0
 */
@SuppressWarnings({"unused"})
public interface AbsService<ID extends Serializable, T extends IEntity<ID>, R extends Repository<ID, T>> extends Repository<ID, T> {
    @Override
    default int save(@Nullable String table, @Nullable Class<?> view, @NonNull Collection<T> entities) {
        return getRepository().save(table, view, entities);
    }

    @Override
    default int insert(@Nullable String table, @Nullable Class<?> view, boolean ignore, @NonNull Collection<T> entities) {
        return getRepository().insert(table, view, ignore, entities);
    }

    @Override
    default int replace(@Nullable String table, @Nullable Class<?> view, @NonNull Collection<T> entities) {
        return getRepository().replace(table, view, entities);
    }

    @Override
    default int update(String table, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, IQuery query) {
        return getRepository().update(table, view, entity, update, selective, ids, query);
    }

    @Override
    default int delete(String table, Collection<ID> ids, IQuery query) {
        return getRepository().delete(table, ids, query);
    }

    @Override
    default List<T> select(String table, Class<?> view, Collection<ID> ids, IQuery query) {
        return getRepository().select(table, view, ids, query);
    }

    @Override
    default T selectOne(String table, Class<?> view, ID id, IQuery query) {
        return getRepository().selectOne(table, view, id, query);
    }

    @Override
    default List<ID> selectIds(@Nullable String table, @NonNull IQuery query) {
        return getRepository().selectIds(table, query);
    }


    @Override
    default long selectCount(String table, Collection<ID> ids, IQuery query) {
        return getRepository().selectCount(table, ids, query);
    }

    @Override
    default void truncate(@Nullable String table) {
        getRepository().truncate(table);
    }

    @NonNull
    R getRepository();

}
