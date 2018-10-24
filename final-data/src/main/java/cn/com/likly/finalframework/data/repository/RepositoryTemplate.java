package cn.com.likly.finalframework.data.repository;

import cn.com.likly.finalframework.data.domain.Criteria;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.Entity;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-22 21:00
 * @since 1.0
 */
public interface RepositoryTemplate<ID extends Serializable, T extends Entity<ID>> {

    /*=========================================================== INSERT ===========================================================*/

    default int insert(EntityHolder<T> holder, T... entities) {
        return insert(holder, Arrays.asList(entities));
    }

    int insert(EntityHolder<T> holder, Collection<T> entities);

    default int insertUuid(EntityHolder<T> holder, T... entities) {
        return insertUuid(holder, Arrays.asList(entities));
    }

    int insertUuid(EntityHolder<T> holder, Collection<T> entities);

    default int insertMd5(EntityHolder<T> holder, T... entities) {
        return insertMd5(holder, Arrays.asList(entities));
    }

    int insertMd5(EntityHolder<T> holder, Collection<T> entities);

    default int insertOther(EntityHolder<T> holder, T... entities) {
        return insertOther(holder, Arrays.asList(entities));
    }

    int insertOther(EntityHolder<T> holder, Collection<T> entities);


    /*=========================================================== UPDATE ===========================================================*/

    default int update(EntityHolder<T> holder, T entity) {
        return update(holder, entity, new Query().where(Criteria.where(holder.getIdProperty()).is(entity.getId())));
    }

    default int update(EntityHolder<T> holder, T entity, Query query) {
        return update(holder, entity, null, query);
    }

    default int update(EntityHolder<T> holder, @Param("update") Update update, ID... ids) {
        return update(holder, update, Arrays.asList(ids));
    }

    default int update(EntityHolder<T> holder, @Param("update") Update update, Collection<ID> ids) {
        return update(holder, update, new Query().where(Criteria.where(holder.getRequiredIdProperty()).in(ids)));
    }

    default int update(EntityHolder<T> holder, @Param("update") Update update, Query query) {
        return update(holder, null, update, query);
    }

    int update(EntityHolder<T> holder, T entity, @Param("update") Update update, Query query);


    /*=========================================================== DELETE ===========================================================*/

    default int delete(EntityHolder<T> holder, T... entities) {
        return delete(holder, Arrays.asList(entities));
    }

    default int delete(EntityHolder<T> holder, List<T> entities) {
        return delete(holder, entities.stream().map(it -> it.getId()).collect(Collectors.toList()));
    }

    default int delete(EntityHolder<T> holder, ID... ids) {
        return delete(holder, Arrays.asList(ids));
    }

    default int delete(EntityHolder<T> holder, Collection<ID> ids) {
        return delete(holder, new Query().where(Criteria.where(holder.getIdProperty()).in(ids)));
    }

    int delete(EntityHolder<T> holder, Query query);


    /*=========================================================== SELECT ===========================================================*/
    default List<T> select(EntityHolder<T> holder, ID... ids) {
        return select(holder, Arrays.asList(ids));
    }

    default List<T> select(EntityHolder<T> holder, Collection<ID> ids) {
        Criteria in = Criteria.where(holder.getRequiredIdProperty()).in(ids);
        return select(holder, new Query().where((Criteria) in));
    }

    List<T> select(EntityHolder<T> holder, Query query);

    default T selectOne(EntityHolder<T> holder, ID id) {
        return selectOne(holder, new Query().where((Criteria) Criteria.where(holder.getRequiredIdProperty()).is(id)));
    }

    T selectOne(EntityHolder<T> holder, Query query);

    default long selectCount(EntityHolder<T> holder) {
        return selectCount(holder, null);
    }

    long selectCount(EntityHolder<T> holder, Query query);


    default boolean isExists(EntityHolder<T> holder, ID id) {
        return selectOne(holder, id) != null;
    }

    default boolean isExists(EntityHolder<T> holder, Query query) {
        return selectCount(holder, query) > 0;
    }

}
