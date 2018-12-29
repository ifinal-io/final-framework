package com.ilikly.finalframework.data.repository;

import com.ilikly.finalframework.data.entity.IEntity;
import com.ilikly.finalframework.data.query.Query;
import com.ilikly.finalframework.data.query.Update;
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
 * @date 2018-10-12 13:27
 * @since 1.0
 */
@SuppressWarnings({"unused", "unchecked"})
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

    /*=========================================== INSERT ===========================================*/
    default int insert(T... entities) {
        return insert(Arrays.asList(entities));
    }

    default int insert(@Param("list") Collection<T> entities) {
        return insert(entities, null);
    }

    default int insert(@Param("list") Collection<T> entities, @Param("query") Query query) {
        return insert(null, entities, query);
    }

    int insert(@Param("tableName") String tableName, @Param("list") Collection<T> entities, @Param("query") Query query);

    /*=========================================== SET ===========================================*/

    default int update(T entity) {
        return update(null, entity, null, Collections.singletonList(entity.getId()), null);
    }

    default int update(T... entities) {
        return update(Arrays.asList(entities));
    }

    default int update(Collection<T> entities) {
        return entities.stream()
                .mapToInt(this::update)
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

    int update(@Param("tableName") String tableName, @Param("entity") T entity, @Param("update") Update update,
               @Param("ids") Collection<ID> ids, @Param("query") Query query);

    /*=========================================== DELETE ===========================================*/

    default int delete(T... entities) {
        return delete(Arrays.asList(entities));
    }

    default int delete(List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(ID... ids) {
        return delete(Arrays.asList(ids));
    }

    default int delete(Collection<ID> ids) {
        return delete(null, ids, null);
    }

    default int delete(Query query) {
        return delete(null, null, query);
    }

    int delete(@Param("tableName") String tableName, @Param("ids") Collection<ID> ids, @Param("query") Query query);

    /*=========================================== SELECT ===========================================*/

    default List<T> select(ID... ids) {
        return select(Arrays.asList(ids));
    }

    default List<T> select(Collection<ID> ids) {
        return select(null, ids, null);
    }

    default List<T> select(Query query) {
        return select(null, null, query);
    }

    List<T> select(@Param("tableName") String tableName, @Param("ids") Collection<ID> ids, @Param("query") Query query);

    default T selectOne(ID id) {
        return selectOne(null, id, null);
    }

    default T selectOne(Query query) {
        return selectOne(null, null, query.limit(1));
    }

    T selectOne(@Param("tableName") String tableName, @Param("id") ID id, @Param("query") Query query);

    default long selectCount() {
        return selectCount(null);
    }

    default long selectCount(@Param("query") Query query) {
        return selectCount(null, query);
    }

    long selectCount(@Param("tableName") String tableName, @Param("query") Query query);

    default boolean isExists(@Param("id") ID id) {
        return selectOne(id) != null;
    }

    default boolean isExists(@Param("query") Query query) {
        return selectCount(query) > 0;
    }


}
