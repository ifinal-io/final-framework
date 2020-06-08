package org.finalframework.data.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Param;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Queryable;
import org.finalframework.data.query.Update;
import org.finalframework.data.repository.Listener;
import org.finalframework.data.repository.Repository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.lang.NonNull;


/**
 * 默认的{@link AbsService}实现，方便其子类通过 {@literal super}调用方法
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-18 16:16:49
 * @since 1.0
 */
public abstract class AbsServiceImpl<ID extends Serializable, T extends IEntity<ID>, R extends Repository<ID, T>> implements AbsService<ID, T, R> {

    @NonNull
    private final R repository;

    public AbsServiceImpl(ObjectProvider<R> repositoryProvider) {
        this.repository = Objects.requireNonNull(repositoryProvider.getIfAvailable());
    }

    /*=========================================== INSERT ===========================================*/
    @Override
    public final int insert(T... entities) {
        return insert(Arrays.asList(entities));
    }

    @Override
    public final int insert(boolean ignore, T... entities) {
        return insert(ignore, Arrays.asList(entities));
    }

    @Override
    public final int insert(Collection<T> entities) {
        return insert(null, null, false, entities);
    }

    @Override
    public final int insert(boolean ignore, Collection<T> entities) {
        return insert(null, null, ignore, entities);
    }


    @Override
    public final int insert(String table, T... entities) {
        return insert(table, Arrays.asList(entities));
    }

    @Override
    public final int insert(String table, boolean ignore, T... entities) {
        return insert(table, ignore, Arrays.asList(entities));
    }

    @Override
    public final int insert(String table, Collection<T> entities) {
        return insert(table, null, false, entities);
    }

    @Override
    public final int insert(String table, boolean ignore, Collection<T> entities) {
        return insert(table, null, ignore, entities);
    }

    @Override
    public final int insert(Class<?> view, T... entities) {
        return insert(view, Arrays.asList(entities));
    }

    @Override
    public final int insert(Class<?> view, boolean ignore, T... entities) {
        return insert(view, ignore, Arrays.asList(entities));
    }

    @Override
    public final int insert(Class<?> view, Collection<T> entities) {
        return insert(null, view, false, entities);
    }

    @Override
    public final int insert(Class<?> view, boolean ignore, Collection<T> entities) {
        return insert(null, view, ignore, entities);
    }

    @Override
    public final int insert(String table, Class<?> view, T... entities) {
        return insert(table, view, false, Arrays.asList(entities));
    }

    @Override
    public final int insert(String table, Class<?> view, boolean ignore, T... entities) {
        return insert(table, view, ignore, Arrays.asList(entities));
    }

    /*=========================================== REPLACE ===========================================*/
    @Override
    public final int replace(T... entities) {
        return replace(Arrays.asList(entities));
    }

    @Override
    public final int replace(Collection<T> entities) {
        return replace(null, null, entities);
    }

    @Override
    public final int replace(String table, T... entities) {
        return replace(table, Arrays.asList(entities));
    }

    @Override
    public final int replace(String table, Collection<T> entities) {
        return replace(table, null, entities);
    }

    @Override
    public final int replace(Class<?> view, T... entities) {
        return replace(view, Arrays.asList(entities));
    }

    @Override
    public final int replace(Class<?> view, Collection<T> entities) {
        return replace(null, view, entities);
    }

    @Override
    public final int replace(String table, Class<?> view, T... entities) {
        return replace(table, view, Arrays.asList(entities));
    }

    /*=========================================== SAVE ===========================================*/
    @Override
    public final int save(T... entities) {
        return save(Arrays.asList(entities));
    }

    @Override
    public final int save(Collection<T> entities) {
        return save(null, null, entities);
    }

    @Override
    public final int save(String table, T... entities) {
        return save(table, Arrays.asList(entities));
    }

    @Override
    public final int save(String table, Collection<T> entities) {
        return save(table, null, entities);
    }

    @Override
    public final int save(Class<?> view, T... entities) {
        return save(view, Arrays.asList(entities));
    }

    @Override
    public final int save(Class<?> view, Collection<T> entities) {
        return save(null, view, entities);
    }

    @Override
    public final int save(String table, Class<?> view, T... entities) {
        return save(table, view, Arrays.asList(entities));
    }

    /*=========================================== UPDATE ===========================================*/

    @Override
    public final int update(T entity) {
        return update((String) null, entity);
    }

    @Override
    public final int update(String table, T entity) {
        return update(table, null, entity);
    }

    @Override
    public final int update(Class<?> view, T entity) {
        return update(null, view, entity);
    }

    @Override
    public final int update(T entity, boolean selective) {
        return update((String) null, entity, selective);
    }

    @Override
    public final int update(T entity, ID... ids) {
        return update(entity, Arrays.asList(ids));
    }

    @Override
    public final int update(T entity, Collection<ID> ids) {
        return update((String) null, entity, ids);
    }

    @Override
    public final int update(T entity, @NonNull Queryable query) {
        return update(entity, query.convert());
    }

    @Override
    public final int update(T entity, Query query) {
        return update((String) null, entity, query);
    }

    @Override
    public final int update(T entity, boolean selective, ID... ids) {
        return update(entity, selective, Arrays.asList(ids));
    }

    @Override
    public final int update(T entity, boolean selective, Collection<ID> ids) {
        return update(null, null, entity, selective, ids);
    }

    @Override
    public final int update(T entity, boolean selective, @NonNull Queryable query) {
        return update(entity, selective, query.convert());
    }

    @Override
    public final int update(T entity, boolean selective, Query query) {
        return update(null, null, entity, selective, query);
    }

    @Override
    public final int update(String table, Class<?> view, T entity) {
        return update(table, view, entity, true);
    }

    @Override
    public final int update(String table, T entity, boolean selective) {
        return update(table, null, entity, selective);
    }

    @Override
    public final int update(String table, T entity, ID... ids) {
        return update(table, entity, Arrays.asList(ids));
    }

    @Override
    public final int update(String table, T entity, Collection<ID> ids) {
        return update(table, null, entity, true, ids);
    }

    @Override
    public final int update(String table, T entity, @NonNull Queryable query) {
        return update(table, entity, query.convert());
    }

    @Override
    public final int update(String table, T entity, Query query) {
        return update(table, null, entity, query);
    }

    @Override
    public final int update(Class<?> view, T entity, boolean selective) {
        return update(null, view, entity, selective);
    }

    @Override
    public final int update(Class<?> view, T entity, ID... ids) {
        return update(view, entity, Arrays.asList(ids));
    }

    @Override
    public final int update(Class<?> view, T entity, Collection<ID> ids) {
        return update(null, view, entity, ids);
    }

    @Override
    public final int update(Class<?> view, T entity, @NonNull Queryable query) {
        return update(view, entity, query.convert());
    }

    @Override
    public final int update(Class<?> view, T entity, Query query) {
        return update(null, view, entity, query);
    }


    @Override
    public final int update(String table, Class<?> view, T entity, ID... ids) {
        return update(table, view, entity, Arrays.asList(ids));
    }

    @Override
    public final int update(String table, Class<?> view, T entity, Collection<ID> ids) {
        return update(table, view, entity, null, true, ids, null);
    }

    @Override
    public final int update(String table, Class<?> view, T entity, @NonNull Queryable query) {
        return update(table, view, entity, query.convert());
    }

    @Override
    public final int update(String table, Class<?> view, T entity, Query query) {
        return update(table, view, entity, null, true, null, query);
    }

    @Override
    public final int update(String table, Class<?> view, T entity, boolean selective) {
        return update(table, view, entity, selective, entity.getId());
    }

    @Override
    public final int update(String table, Class<?> view, T entity, boolean selective, ID... ids) {
        return update(table, view, entity, selective, Arrays.asList(ids));
    }

    @Override
    public final int update(String table, Class<?> view, T entity, boolean selective, Collection<ID> ids) {
        return update(table, view, entity, null, selective, ids, null);
    }

    @Override
    public final int update(String table, Class<?> view, T entity, boolean selective, @NonNull Queryable query) {
        return update(table, view, entity, selective, query.convert());
    }

    @Override
    public final int update(String table, Class<?> view, T entity, boolean selective, Query query) {
        return update(table, view, entity, null, selective, null, query);
    }


    @Override
    public final int update(T... entities) {
        return update(Arrays.asList(entities));
    }

    @Override
    public final int update(@NonNull Collection<T> entities) {
        return entities.stream()
                .mapToInt(this::update)
                .sum();
    }

    @Override
    public final int update(String table, T... entities) {
        return update(table, Arrays.asList(entities));
    }

    @Override
    public final int update(String table, Collection<T> entities) {
        return update(table, null, entities);
    }

    @Override
    public final int update(Class<?> view, T... entities) {
        return update(view, Arrays.asList(entities));
    }

    @Override
    public final int update(Class<?> view, Collection<T> entities) {
        return update(null, view, entities);
    }

    @Override
    public final int update(boolean selective, T... entities) {
        return update(Arrays.asList(entities), selective);
    }

    @Override
    public final int update(@NonNull Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(it, selective))
                .sum();
    }

    @Override
    public final int update(String table, Class<?> view, Collection<T> entities) {
        return update(table, view, entities, true);
    }

    @Override
    public final int update(String table, Collection<T> entities, boolean selective) {
        return update(table, null, entities, selective);
    }

    @Override
    public final int update(String table, Class<?> view, Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(table, view, it, selective))
                .sum();
    }


    // -----------------Update---------

    @Override
    public final int update(Update update, ID... ids) {
        return update(update, Arrays.asList(ids));
    }

    @Override
    public final int update(Update update, Collection<ID> ids) {
        return update(null, null, null, update, false, ids, null);
    }

    @Override
    public final int update(String table, Update update, ID... ids) {
        return update(table, update, Arrays.asList(ids));
    }

    @Override
    public final int update(String table, Update update, Collection<ID> ids) {
        return update(table, null, null, update, false, ids, null);
    }

    @Override
    public final int update(Update update, @NonNull Queryable query) {
        return update(update, query.convert());
    }

    @Override
    public final int update(Update update, Query query) {
        return update(null, update, query);
    }

    @Override
    public final int update(String table, Update update, @NonNull Queryable query) {
        return update(table, update, query.convert());
    }

    @Override
    public final int update(String table, Update update, Query query) {
        return update(table, null, null, update, false, null, query);
    }

    /*=========================================== DELETE ===========================================*/

    @Override
    public final int delete(T... entities) {
        return delete(Arrays.asList(entities));
    }

    @Override
    public final int delete(String table, T... entities) {
        return delete(table, Arrays.asList(entities));
    }

    @Override
    public final int delete(@NonNull List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    @Override
    public final int delete(String table, @NonNull List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    @Override
    public final int delete(ID... ids) {
        return delete(Arrays.asList(ids));
    }

    @Override
    public final int delete(String table, ID... ids) {
        return delete(table, Arrays.asList(ids));
    }

    @Override
    public final int delete(Collection<ID> ids) {
        return delete(null, ids);
    }

    @Override
    public final int delete(String table, Collection<ID> ids) {
        return delete(table, ids, null);
    }

    @Override
    public final int delete(@NonNull Queryable query) {
        return delete(query.convert());
    }

    @Override
    public final int delete(Query query) {
        return delete(null, null, query);
    }

    @Override
    public final int delete(String table, @NonNull Queryable query) {
        return delete(table, query.convert());
    }

    @Override
    public final int delete(String table, Query query) {
        return delete(table, null, query);
    }


    @Override
    public final <PARAM> void delete(Query query, Listener<PARAM, Integer> listener) {
        this.delete(null, query, listener);
    }

    @Override
    public final <PARAM> void delete(String table, @NonNull Query query, Listener<PARAM, Integer> listener) {
        Long limit = query.getLimit().getLimit();
        Assert.isNull(limit, "limit is null");
        Assert.isNull(listener, "listener is null");
        int offset = 0;
        PARAM param = listener.onInit();
        listener.onStart(param);
        while (true) {
            int rows = delete(table, query);
            if (!listener.onListening(offset, param, rows)) break;
            if (rows < limit) break;
        }
        listener.onFinish(param);
    }

    /*=========================================== SELECT ===========================================*/

    @Override
    public final List<T> select() {
        return select((Query) null);
    }

    @Override
    public final List<T> select(String table) {
        return select(table, (Query) null);
    }

    @Override
    public final List<T> select(Class<?> view) {
        return select(view, (Query) null);
    }

    @Override
    public final List<T> select(String table, Class<?> view) {
        return select(table, view, (Query) null);
    }

    @Override
    public final List<T> select(ID... ids) {
        return select(Arrays.asList(ids));
    }

    @Override
    public final List<T> select(String table, ID... ids) {
        return select(table, Arrays.asList(ids));
    }

    @Override
    public final List<T> select(Class<?> view, ID... ids) {
        return select(view, Arrays.asList(ids));
    }

    @Override
    public final List<T> select(String table, Class<?> view, ID... ids) {
        return select(table, view, Arrays.asList(ids));
    }

    @Override
    public final List<T> select(Collection<ID> ids) {
        return select(null, null, ids);
    }

    @Override
    public final List<T> select(String table, Collection<ID> ids) {
        return select(table, null, ids, null);
    }

    @Override
    public final List<T> select(Class<?> view, Collection<ID> ids) {
        return select(null, view, ids);
    }

    @Override
    public final List<T> select(String table, Class<?> view, Collection<ID> ids) {
        return select(table, view, ids, null);
    }

    @Override
    public final List<T> select(@NonNull Queryable query) {
        return select(query.convert());
    }


    @Override
    public final List<T> select(Query query) {
        return select(null, null, query);
    }

    @Override
    public final List<T> select(String table, @NonNull Queryable query) {
        return select(table, query.convert());
    }

    @Override
    public final List<T> select(String table, Query query) {
        return select(table, null, null, query);
    }

    @Override
    public final List<T> select(Class<?> view, @NonNull Queryable query) {
        return select(view, query.convert());
    }

    @Override
    public final List<T> select(Class<?> view, @NonNull Query query) {
        return select(null, view, query);
    }

    @Override
    public final List<T> select(String table, Class<?> view, @NonNull Queryable query) {
        return select(table, view, query.convert());
    }

    @Override
    public final List<T> select(String table, Class<?> view, @NonNull Query query) {
        return select(table, view, null, query);
    }

    /*=========================================== SELECT ONE ===========================================*/

    @Override
    public final T selectOne(ID id) {
        return selectOne(null, null, id, null);
    }

    @Override
    public final T selectOne(String table, ID id) {
        return selectOne(table, null, id, null);
    }

    @Override
    public final T selectOne(Class<?> view, ID id) {
        return selectOne(null, view, id, null);
    }

    @Override
    public final T selectOne(String table, Class<?> view, ID id) {
        return selectOne(table, view, id, null);
    }

    @Override
    public final T selectOne(@NonNull Queryable query) {
        return selectOne(query.convert());
    }

    @Override
    public final T selectOne(@NonNull Query query) {
        return selectOne(null, null, query.limit(1));
    }

    @Override
    public final T selectOne(String table, @NonNull Queryable query) {
        return selectOne(table, null, query.convert().limit(1));
    }


    @Override
    public final T selectOne(String table, @NonNull Query query) {
        return selectOne(table, null, query.limit(1));
    }

    @Override
    public final T selectOne(Class<?> view, @NonNull Queryable query) {
        return selectOne(view, query.convert().limit(1));
    }

    @Override
    public final T selectOne(Class<?> view, @NonNull Query query) {
        return selectOne(null, view, null, query.limit(1));
    }

    @Override
    public final T selectOne(String table, Class<?> view, @NonNull Queryable query) {
        return selectOne(table, view, query.convert().limit(1));
    }

    @Override
    public final T selectOne(String table, Class<?> view, @NonNull Query query) {
        return selectOne(table, view, null, query.limit(1));
    }



    /*=========================================== SCANNER ===========================================*/

    @Override
    public final <PARAM> void scan(@NonNull Queryable query, Listener<PARAM, List<T>> listener) {
        scan(query.convert(), listener);
    }

    @Override
    public final <PARAM> void scan(Query query, Listener<PARAM, List<T>> listener) {
        scan(null, null, query, listener);
    }

    @Override
    public final <PARAM> void scan(Class<?> view, @NonNull Queryable query, Listener<PARAM, List<T>> listener) {
        scan(view, query.convert(), listener);
    }

    @Override
    public final <PARAM> void scan(Class<?> view, @NonNull Query query, Listener<PARAM, List<T>> listener) {
        scan(null, view, query, listener);
    }

    @Override
    public final <PARAM> void scan(String table, @NonNull Queryable query, Listener<PARAM, List<T>> listener) {
        scan(table, query.convert(), listener);
    }

    @Override
    public final <PARAM> void scan(String table, Query query, Listener<PARAM, List<T>> listener) {
        scan(table, null, query, listener);
    }

    @Override
    public final <PARAM> void scan(@Param("table") String table, @Param("view") Class<?> view, @Param("query") Queryable query, Listener<PARAM, List<T>> listener) {
        scan(table, view, query.convert(), listener);
    }

    @Override
    public final <PARAM> void scan(String table, Class<?> view, @NonNull Query query, Listener<PARAM, List<T>> listener) {
        if (Assert.isNull(query.getPage()) || Assert.isNull(query.getSize())) {
            throw new IllegalArgumentException("query page or size is null");
        }
        Assert.isNull(listener, "listener is null");
        int index = query.getPage();
        int offset = 0;
        PARAM param = listener.onInit();
        listener.onStart(param);
        while (true) {
            query.page(index + offset);
            List<T> list = select(table, view, query);
            offset++;
            if (!listener.onListening(offset, param, list)) break;
            if (Assert.isEmpty(list) || list.size() < query.getSize()) break;
        }
        listener.onFinish(param);
    }

    /*=========================================== SELECT IDS===========================================*/

    @Override
    public final List<ID> selectIds(@NonNull Queryable query) {
        return selectIds(query.convert());
    }

    @Override
    public final List<ID> selectIds(Query query) {
        return selectIds(null, query);
    }

    @Override
    public final List<ID> selectIds(String table, @NonNull Queryable query) {
        return selectIds(table, query.convert());
    }

    /*=========================================== SELECT COUNT ===========================================*/

    @Override
    public final long selectCount() {
        return selectCount((Query) null);
    }

    @Override
    public final long selectCount(String table) {
        return selectCount(table, null, null);
    }

    @Override
    public final long selectCount(@NonNull Queryable query) {
        return selectCount(query.convert());
    }

    @Override
    public final long selectCount(Query query) {
        return selectCount(null, null, query);
    }


    @Override
    public final long selectCount(String table, @NonNull Queryable query) {
        return selectCount(table, null, query.convert());
    }

    @Override
    public final long selectCount(String table, Query query) {
        return selectCount(table, null, query);
    }

    @Override
    public final long selectCount(ID... ids) {
        return selectCount(Arrays.asList(ids));
    }

    @Override
    public final long selectCount(Collection<ID> ids) {
        return selectCount(null, ids);
    }

    @Override
    public final long selectCount(String table, ID... ids) {
        return selectCount(table, Arrays.asList(ids));
    }

    @Override
    public final long selectCount(String table, Collection<ID> ids) {
        return selectCount(table, ids, null);
    }


    /*=========================================== IS EXISTS ===========================================*/
    @Override
    public final boolean isExists(ID id) {
        return selectOne(id) != null;
    }

    @Override
    public final boolean isExists(String table, ID id) {
        return selectOne(table, id) != null;
    }

    @Override
    public final boolean isExists(@NonNull Queryable query) {
        return isExists(query.convert());
    }

    @Override
    public final boolean isExists(Query query) {
        return selectCount(query) > 0;
    }

    @Override
    public final boolean isExists(String table, @NonNull Queryable query) {
        return isExists(table, query.convert());
    }

    @Override
    public final boolean isExists(String table, Query query) {
        return selectCount(table, null, query) > 0;
    }


    /*=========================================== TRUNCATE ===========================================*/

    @Override
    public final void truncate() {
        truncate(null);
    }

    @Override
    public final R getRepository() {
        return repository;
    }

    /*=========================================== Overridable ===========================================*/



}

