package org.finalframework.data.repository;

import org.apache.ibatis.annotations.Param;
import org.finalframework.data.entity.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:27
 * @since 1.0
 */
@SuppressWarnings({"unused", "unchecked"})
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

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

    default int insert(Collection<T> entities, Query query) {
        return insert(null, null, entities, query);
    }

    default int insert(String tableName, Collection<T> entities, Query query) {
        return insert(tableName, null, entities, query);
    }

    default int insert(Class<?> view, Collection<T> entities, Query query) {
        return insert(null, view, entities, query);
    }

    int insert(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("list") Collection<T> entities, @Param("query") Query query);

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

    default int update(T entity, Query query) {
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
        return update(tableName, null, entity, true, ids);
    }

    default int update(String tableName, T entity, Query query) {
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

    default int update(Class<?> view, T entity, Query query) {
        return update(null, view, entity, query);
    }


    default int update(String tableName, Class<?> view, T entity, ID... ids) {
        return update(tableName, view, entity, Arrays.asList(ids));
    }

    default int update(String tableName, Class<?> view, T entity, Collection<ID> ids) {
        return update(tableName, view, entity, null, true, ids, null);
    }

    default int update(String tableName, Class<?> view, T entity, Query query) {
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

    default int update(String tableName, Class<?> view, T entity, boolean selective, Query query) {
        return update(tableName, view, entity, null, selective, null, query);
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, Collection<ID> ids, Query query) {
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

    default int update(Update update, Query query) {
        return update(null, update, query);
    }

    default int update(String tableName, Update update, Query query) {
        return update(null, null, null, update, false, null, query);
    }

    int update(@Param("tableName") String tableName, @Param("view") Class<?> view,
               @Param("entity") T entity, @Param("update") Update update, @Param("selective") boolean selective,
               @Param("ids") Collection<ID> ids, @Param("query") Query query);

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

    default int delete(Query query) {
        return delete(null, null, query);
    }

    default int delete(String tableName, Query query) {
        return delete(tableName, null, query);
    }

    int delete(@Param("tableName") String tableName, @Param("ids") Collection<ID> ids, @Param("query") Query query);

    /*=========================================== SELECT ===========================================*/

    default List<T> select() {
        return select((Query) null);
    }

    default List<T> select(String tableName) {
        return select(tableName, (Query) null);
    }

    default List<T> select(Class<?> view) {
        return select(view, (Query) null);
    }

    default List<T> select(String tableName, Class<?> view) {
        return select(tableName, view, (Query) null);
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

    default List<T> select(Query query) {
        return select(null, null, query);
    }

    default List<T> select(String tableName, Query query) {
        return select(tableName, null, null, query);
    }

    default List<T> select(Class<?> view, Query query) {
        return select(null, view, query);
    }

    default List<T> select(String tableName, Class<?> view, Query query) {
        return select(tableName, view, null, query);
    }

    List<T> select(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("ids") Collection<ID> ids, @Param("query") Query query);

    /*=========================================== SELECT IDS===========================================*/

    default List<ID> selectIds(Query query) {
        return selectIds(null, query);
    }

    List<ID> selectIds(@Param("tableName") String tableName, @Param("query") Query query);

    /*=========================================== SELECT ONE ===========================================*/

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

    default T selectOne(Query query) {
        return selectOne(null, null, query.limit(1));
    }

    default T selectOne(String tableName, Query query) {
        return selectOne(tableName, null, query.limit(1));
    }

    default T selectOne(Class<?> view, Query query) {
        return selectOne(null, view, null, query.limit(1));
    }

    default T selectOne(String tableName, Class<?> view, Query query) {
        return selectOne(tableName, view, null, query.limit(1));
    }

    T selectOne(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("id") ID id, @Param("query") Query query);

    /*=========================================== SELECT COUNT ===========================================*/

    default long selectCount() {
        return selectCount((Query) null);
    }

    default long selectCount(String tableName) {
        return selectCount(tableName, null);
    }

    default long selectCount(Query query) {
        return selectCount(null, query);
    }

    long selectCount(@Param("tableName") String tableName, @Param("query") Query query);

    /*=========================================== IS EXISTS ===========================================*/
    default boolean isExists(ID id) {
        return selectOne(id) != null;
    }

    default boolean isExists(String tableName, ID id) {
        return selectOne(tableName, id) != null;
    }

    default boolean isExists(Query query) {
        return selectCount(query) > 0;
    }

    default boolean isExists(String tableName, Query query) {
        return selectCount(tableName, query) > 0;
    }


}
