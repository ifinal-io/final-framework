package org.ifinal.finalframework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.data.repository.Repository;
import org.ifinal.finalframework.query.Update;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"unused"})
public interface AbsService<I extends Serializable, T extends IEntity<I>, R extends Repository<I, T>>
    extends Repository<I, T> {

    @Override
    default int save(@Nullable String table, @Nullable Class<?> view, @NonNull Collection<T> entities) {
        return getRepository().save(table, view, entities);
    }

    @Override
    default int insert(@Nullable String table, @Nullable Class<?> view,
        boolean ignore, @NonNull Collection<T> entities) {
        return getRepository().insert(table, view, ignore, entities);
    }

    @Override
    default int replace(@Nullable String table, @Nullable Class<?> view, @NonNull Collection<T> entities) {
        return getRepository().replace(table, view, entities);
    }

    @Override
    default int update(String table, Class<?> view, T entity, Update update,
        boolean selective, Collection<I> ids, IQuery query) {
        return getRepository().update(table, view, entity, update, selective, ids, query);
    }

    @Override
    default int delete(String table, Collection<I> ids, IQuery query) {
        return getRepository().delete(table, ids, query);
    }

    @Override
    default List<T> select(String table, Class<?> view, Collection<I> ids, IQuery query) {
        return getRepository().select(table, view, ids, query);
    }

    @Override
    default T selectOne(String table, Class<?> view, I id, IQuery query) {
        return getRepository().selectOne(table, view, id, query);
    }

    @Override
    default List<I> selectIds(@Nullable String table, @NonNull IQuery query) {
        return getRepository().selectIds(table, query);
    }

    @Override
    default long selectCount(String table, Collection<I> ids, IQuery query) {
        return getRepository().selectCount(table, ids, query);
    }

    @Override
    default void truncate(@Nullable String table) {
        getRepository().truncate(table);
    }

    @NonNull
    R getRepository();

}
