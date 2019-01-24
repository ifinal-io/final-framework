package com.ilikly.finalframework.data.service;

import com.ilikly.finalframework.data.entity.IEntity;
import com.ilikly.finalframework.data.query.Query;
import com.ilikly.finalframework.data.query.Update;
import com.ilikly.finalframework.data.repository.Repository;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-24 11:14:03
 * @since 1.0
 */
public interface AbsService<ID extends Serializable, T extends IEntity<ID>, R extends Repository<ID, T>> {

    /*=========================================== INSERT ===========================================*/
    default int insert(T... entities) {
        return insert(Arrays.asList(entities));
    }

    default int insert(String tableName, T... entities) {
        return insert(tableName, Arrays.asList(entities));
    }

    default int insert(@Param("list") Collection<T> entities) {
        return insert(entities, null);
    }

    default int insert(String tableName, Collection<T> entities) {
        return insert(tableName, entities, null);
    }

    default int insert(@Param("list") Collection<T> entities, @Param("query") Query query) {
        return insert(null, entities, query);
    }

    default int insert(@Param("tableName") String tableName, @Param("list") Collection<T> entities, @Param("query") Query query) {
        return getRepository().insert(tableName, entities, query);
    }

    /*=========================================== SET ===========================================*/

    default int update(T entity) {
        return update(null, entity);
    }

    default int update(String tableName, T entity) {
        return update(tableName, entity, null, Collections.singletonList(entity.getId()), null);
    }

    default int update(T... entities) {
        return update(Arrays.asList(entities));
    }

    default int update(String tableName, T... entities) {
        return update(tableName, Arrays.asList(entities));
    }

    default int update(Collection<T> entities) {
        return entities.stream()
                .mapToInt(this::update)
                .sum();
    }

    default int update(String tableName, Collection<T> entities) {
        return entities.stream()
                .mapToInt(it -> update(tableName, it))
                .sum();
    }

    default int update(Update update, ID... ids) {
        return update(update, Arrays.asList(ids));
    }

    default int update(Update update, Collection<ID> ids) {
        return update(null, null, update, ids, null);
    }

    default int update(Update update, Query query) {
        return update(null, null, update, null, query);
    }

    default int update(String tableName, T entity, Update update,
                       Collection<ID> ids, Query query) {
        return getRepository().update(tableName, entity, update, ids, query);
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

    default int delete(Query query) {
        return delete(null, null, query);
    }

    default int delete(String tableName, Query query) {
        return delete(tableName, null, query);
    }

    default int delete(String tableName, Collection<ID> ids, Query query) {
        return getRepository().delete(tableName, ids, query);
    }

    /*=========================================== SELECT ===========================================*/

    default List<T> select() {
        return select((Query) null);
    }

    default List<T> select(String tableName) {
        return select(tableName, (Query) null);
    }

    default List<T> select(ID... ids) {
        return select(Arrays.asList(ids));
    }

    default List<T> select(String tableName, ID... ids) {
        return select(tableName, Arrays.asList(ids));
    }


    default List<T> select(Collection<ID> ids) {
        return select(null, ids);
    }

    default List<T> select(String tableName, Collection<ID> ids) {
        return select(tableName, ids, null);
    }

    default List<T> select(Query query) {
        return select(null, query);
    }

    default List<T> select(String tableName, Query query) {
        return select(tableName, null, query);
    }

    default List<T> select(String tableName, Collection<ID> ids, Query query) {
        return getRepository().select(tableName, ids, query);
    }

    default T selectOne(ID id) {
        return selectOne(null, id, null);
    }

    default T selectOne(String tableName, ID id) {
        return selectOne(tableName, id, null);
    }

    default T selectOne(Query query) {
        return selectOne(null, null, query.limit(1));
    }

    default T selectOne(String tableName, Query query) {
        return selectOne(tableName, null, query.limit(1));
    }

    default T selectOne(String tableName, ID id, Query query) {
        return getRepository().selectOne(tableName, id, query);
    }

    default long selectCount() {
        return selectCount((Query) null);
    }

    default long selectCount(String tableName) {
        return selectCount(tableName, null);
    }

    default long selectCount(Query query) {
        return selectCount(null, query);
    }

    default long selectCount(String tableName, Query query) {
        return getRepository().selectCount(tableName, query);
    }

    default boolean isExists(ID id) {
        return selectOne(id) != null;
    }

    default boolean isExists(Query query) {
        return selectCount(query) > 0;
    }

    R getRepository();

}
