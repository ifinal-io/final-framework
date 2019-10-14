package org.finalframework.data.service;

import org.apache.ibatis.annotations.Param;
import org.finalframework.data.entity.IEntity;
import org.finalframework.data.query.QueryImpl;
import org.finalframework.data.query.Update;
import org.finalframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-24 11:14:03
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface AbsService<ID extends Serializable, T extends IEntity<ID>, R extends Repository<ID, T>> {

    /*=========================================== INSERT ===========================================*/
    default int insert(T... entities) {
        return insert(Arrays.asList(entities));
    }

    default int insert(Collection<T> entities) {
        return insert(null, null, entities);
    }

    default int insert(String tableName, T... entities) {
        return insert(tableName, Arrays.asList(entities));
    }

    default int insert(String tableName, Collection<T> entities) {
        return insert(tableName, null, entities, null);
    }

    default int insert(Class<?> view, T... entities) {
        return insert(view, Arrays.asList(entities));
    }

    default int insert(Class<?> view, Collection<T> entities) {
        return insert(null, view, entities);
    }

    default int insert(String tableName, Class<?> view, T... entities) {
        return insert(tableName, view, Arrays.asList(entities));
    }

    default int insert(String tableName, Class<?> view, Collection<T> entities) {
        return insert(tableName, view, entities, null);
    }

    default int insert(Collection<T> entities, QueryImpl query) {
        return insert(null, null, entities, query);
    }

    default int insert(String tableName, Collection<T> entities, QueryImpl query) {
        return insert(tableName, null, entities, query);
    }

    default int insert(Class<?> view, Collection<T> entities, QueryImpl query) {
        return insert(null, view, entities, query);
    }

    default int insert(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("list") Collection<T> entities, @Param("query") QueryImpl query) {
        return getRepository().insert(tableName, view, entities, query);
    }

    /*=========================================== UPDATE ===========================================*/

    default int update(T entity) {
        return update((String) null, entity);
    }

    default int update(String tableName, T entity) {
        return update(tableName, null, entity);
    }

    default int update(Class<?> view, T entity) {
        return update(null, view, entity);
    }

    default int update(T entity, boolean selective) {
        return update((String) null, entity, selective);
    }

    default int update(T entity, ID... ids) {
        return update(entity, Arrays.asList(ids));
    }

    default int update(T entity, Collection<ID> ids) {
        return update((String) null, entity, ids);
    }

    default int update(T entity, QueryImpl query) {
        return update((String) null, entity, query);
    }

    default int update(String tableName, Class<?> view, T entity) {
        return update(tableName, view, entity, true);
    }

    default int update(String tableName, T entity, boolean selective) {
        return update(tableName, null, entity, selective);
    }

    default int update(String tableName, T entity, ID... ids) {
        return update(tableName, entity, Arrays.asList(ids));
    }

    default int update(String tableName, T entity, Collection<ID> ids) {
        return update(tableName, null, entity, true);
    }

    default int update(String tableName, T entity, QueryImpl query) {
        return update(tableName, null, entity, query);
    }

    default int update(Class<?> view, T entity, boolean selective) {
        return update(null, view, entity, selective);
    }

    default int update(Class<?> view, T entity, ID... ids) {
        return update(view, entity, Arrays.asList(ids));
    }

    default int update(Class<?> view, T entity, Collection<ID> ids) {
        return update(null, view, entity, ids);
    }

    default int update(Class<?> view, T entity, QueryImpl query) {
        return update(null, view, entity, query);
    }


    default int update(String tableName, Class<?> view, T entity, ID... ids) {
        return update(tableName, view, entity, Arrays.asList(ids));
    }

    default int update(String tableName, Class<?> view, T entity, Collection<ID> ids) {
        return update(tableName, view, entity, null, true, ids, null);
    }

    default int update(String tableName, Class<?> view, T entity, QueryImpl query) {
        return update(tableName, view, entity, null, true, null, query);
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective) {
        return update(tableName, view, entity, selective, entity.getId());
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, ID... ids) {
        return update(tableName, view, entity, selective, Arrays.asList(ids));
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, Collection<ID> ids) {
        return update(tableName, view, entity, null, selective, ids, null);
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, QueryImpl query) {
        return update(tableName, view, entity, null, selective, null, query);
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, Collection<ID> ids, QueryImpl query) {
        return update(tableName, view, entity, null, selective, ids, query);
    }

    default int update(T... entities) {
        return update(Arrays.asList(entities));
    }

    default int update(Collection<T> entities) {
        return entities.stream()
                .mapToInt(this::update)
                .sum();
    }

    default int update(String tableName, T... entities) {
        return update(tableName, Arrays.asList(entities));
    }

    default int update(String tableName, Collection<T> entities) {
        return update(tableName, null, entities);
    }

    default int update(Class<?> view, T... entities) {
        return update(view, Arrays.asList(entities));
    }

    default int update(Class<?> view, Collection<T> entities) {
        return update(null, view, entities);
    }

    default int update(boolean selective, T... entities) {
        return update(Arrays.asList(entities), selective);
    }

    default int update(Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(it, selective))
                .sum();
    }

    default int update(String tableName, Class<?> view, Collection<T> entities) {
        return update(tableName, view, entities, true);
    }

    default int update(String tableName, Collection<T> entities, boolean selective) {
        return update(tableName, null, entities, selective);
    }

    default int update(String tableName, Class<?> view, Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(tableName, view, it, selective))
                .sum();
    }


    // -----------------Update---------

    default int update(Update update, ID... ids) {
        return update(update, Arrays.asList(ids));
    }

    default int update(Update update, Collection<ID> ids) {
        return update(null, null, null, update, false, ids, null);
    }

    default int update(String tableName, Update update, ID... ids) {
        return update(tableName, update, Arrays.asList(ids));
    }

    default int update(String tableName, Update update, Collection<ID> ids) {
        return update(tableName, null, null, update, false, ids, null);
    }

    default int update(Update update, QueryImpl query) {
        return update(null, update, query);
    }

    default int update(String tableName, Update update, QueryImpl query) {
        return update(null, null, null, update, false, null, query);
    }

    default int update(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, QueryImpl query) {
        return getRepository().update(tableName, view, entity, update, selective, ids, query);
    }

    /*=========================================== DELETE ===========================================*/

    default int delete(T... entities) {
        return delete(Arrays.asList(entities));
    }

    default int delete(String tableName, T... entities) {
        return delete(tableName, Arrays.asList(entities));
    }

    default int delete(List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(String tableName, List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(ID... ids) {
        return delete(Arrays.asList(ids));
    }

    default int delete(String tableName, ID... ids) {
        return delete(tableName, Arrays.asList(ids));
    }

    default int delete(Collection<ID> ids) {
        return delete(null, ids);
    }

    default int delete(String tableName, Collection<ID> ids) {
        return delete(tableName, ids, null);
    }

    default int delete(QueryImpl query) {
        return delete(null, null, query);
    }

    default int delete(String tableName, QueryImpl query) {
        return delete(tableName, null, query);
    }

    default int delete(@Param("tableName") String tableName, @Param("ids") Collection<ID> ids, @Param("query") QueryImpl query) {
        return getRepository().delete(tableName, ids, query);
    }

    /*=========================================== SELECT ===========================================*/

    default List<T> select() {
        return select((QueryImpl) null);
    }

    default List<T> select(String tableName) {
        return select(tableName, (QueryImpl) null);
    }

    default List<T> select(Class<?> view) {
        return select(view, (QueryImpl) null);
    }

    default List<T> select(String tableName, Class<?> view) {
        return select(tableName, view, (QueryImpl) null);
    }

    default List<T> select(ID... ids) {
        return select(Arrays.asList(ids));
    }

    default List<T> select(String tableName, ID... ids) {
        return select(tableName, Arrays.asList(ids));
    }

    default List<T> select(Class<?> view, ID... ids) {
        return select(view, Arrays.asList(ids));
    }

    default List<T> select(String tableName, Class<?> view, ID... ids) {
        return select(tableName, view, Arrays.asList(ids));
    }

    default List<T> select(Collection<ID> ids) {
        return select(null, null, ids);
    }

    default List<T> select(String tableName, Collection<ID> ids) {
        return select(tableName, null, ids, null);
    }

    default List<T> select(Class<?> view, Collection<ID> ids) {
        return select(null, view, ids);
    }

    default List<T> select(String tableName, Class<?> view, Collection<ID> ids) {
        return select(tableName, view, ids, null);
    }

    default List<T> select(QueryImpl query) {
        return select(null, null, query);
    }

    default List<T> select(String tableName, QueryImpl query) {
        return select(tableName, null, null, query);
    }

    default List<T> select(Class<?> view, QueryImpl query) {
        return select(null, view, query);
    }

    default List<T> select(String tableName, Class<?> view, QueryImpl query) {
        return select(tableName, view, null, query);
    }

    default List<T> select(String tableName, Class<?> view, Collection<ID> ids, QueryImpl query) {
        return getRepository().select(tableName, view, ids, query);
    }

    default T selectOne(ID id) {
        return selectOne(null, null, id, null);
    }

    default T selectOne(String tableName, ID id) {
        return selectOne(tableName, null, id, null);
    }

    default T selectOne(Class<?> view, ID id) {
        return selectOne(null, view, id, null);
    }

    default T selectOne(String tableName, Class<?> view, ID id) {
        return selectOne(tableName, view, id, null);
    }

    default T selectOne(QueryImpl query) {
        return selectOne(null, null, query.limit(1));
    }

    default T selectOne(String tableName, QueryImpl query) {
        return selectOne(tableName, null, query.limit(1));
    }

    default T selectOne(Class<?> view, QueryImpl query) {
        return selectOne(null, view, null, query.limit(1));
    }

    default T selectOne(String tableName, Class<?> view, QueryImpl query) {
        return selectOne(tableName, view, null, query.limit(1));
    }

    default T selectOne(String tableName, Class<?> view, @Param("id") ID id, QueryImpl query) {
        return getRepository().selectOne(tableName, view, id, query);
    }

    default long selectCount() {
        return selectCount((QueryImpl) null);
    }

    default long selectCount(String tableName) {
        return selectCount(tableName, null);
    }

    default long selectCount(QueryImpl query) {
        return selectCount(null, query);
    }

    default long selectCount(String tableName, QueryImpl query) {
        return getRepository().selectCount(tableName, query);
    }

    default boolean isExists(ID id) {
        return selectOne(id) != null;
    }

    default boolean isExists(String tableName, ID id) {
        return selectOne(tableName, id) != null;
    }

    default boolean isExists(QueryImpl query) {
        return selectCount(query) > 0;
    }

    default boolean isExists(String tableName, QueryImpl query) {
        return selectCount(tableName, query) > 0;
    }


    @NonNull
    R getRepository();

}
