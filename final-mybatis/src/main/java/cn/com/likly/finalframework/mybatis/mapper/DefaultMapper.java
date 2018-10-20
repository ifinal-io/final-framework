package cn.com.likly.finalframework.mybatis.mapper;

import cn.com.likly.finalframework.data.domain.Criteria;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.Entity;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.mybatis.provider.MapperProvider;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 17:49
 * @since 1.0
 */
@SuppressWarnings("all")
@Mapper
public interface DefaultMapper<ID extends Serializable, T extends Entity<ID>> {

    /*=========================================================== INSERT ===========================================================*/

    default int insert(@Param("holder") EntityHolder<T> holder, @Param("array") T... entities) {
        return insert(holder, Arrays.asList(entities));
    }

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insert(@Param("holder") EntityHolder<T> holder, @Param("list") Collection<T> entities);

    default int insertUuid(@Param("holder") EntityHolder<T> holder, @Param("array") T... entities) {
        return insertUuid(holder, Arrays.asList(entities));
    }

    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insertUuid(@Param("holder") EntityHolder<T> holder, @Param("list") Collection<T> entities);

    default int insertMd5(@Param("holder") EntityHolder<T> holder, @Param("array") T... entities) {
        return insertMd5(holder, Arrays.asList(entities));
    }

    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insertMd5(@Param("holder") EntityHolder<T> holder, @Param("list") Collection<T> entities);

    default int insertOther(@Param("holder") EntityHolder<T> holder, @Param("array") T... entities) {
        return insertOther(holder, Arrays.asList(entities));
    }

    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insertOther(@Param("holder") EntityHolder<T> holder, @Param("list") Collection<T> entities);


    /*=========================================================== SET ===========================================================*/

    default int update(@Param("holder") EntityHolder<T> holder, @Param("entity") T entity) {
        return update(holder, entity, new Query().where(Criteria.where(holder.getIdProperty().getColumn()).is(entity.getId())));
    }

    default int update(@Param("holder") EntityHolder<T> holder, @Param("entity") T entity, @Param("query") Query<PropertyHolder> query) {
        return update(holder, entity, null, query);
    }

    default int update(@Param("holder") EntityHolder<T> holder, @Param("update") Update<PropertyHolder> update, @Param("ids") ID... ids) {
        return update(holder, update, Arrays.asList(ids));
    }

    default int update(@Param("holder") EntityHolder<T> holder, @Param("update") Update<PropertyHolder> update, @Param("ids") Collection<ID>... ids) {
        return update(holder, update, new Query().where(Criteria.where(holder.getRequiredIdProperty().getColumn()).in(ids)));
    }

    default int update(@Param("holder") EntityHolder<T> holder, @Param("update") Update<PropertyHolder> update, @Param("query") Query<PropertyHolder> query) {
        return update(holder, null, update, query);
    }

    int update(@Param("holder") EntityHolder<T> holder, @Param("entity") T entity, @Param("update") Update<PropertyHolder> update, @Param("query") Query<PropertyHolder> query);


    /*=========================================================== DELETE ===========================================================*/

    default int delete(@Param("holder") EntityHolder<T> holder, @Param("array") T... entities) {
        return delete(holder, Arrays.asList(entities));
    }

    default int delete(@Param("holder") EntityHolder<T> holder, @Param("list") Collection<T> entities) {
        return delete(holder, entities.stream().map(it -> it.getId()).collect(Collectors.toList()));
    }

    default int delete(@Param("holder") EntityHolder<T> holder, @Param("ids") ID... ids) {
        return delete(holder, Arrays.asList(ids));
    }

    default int delete(@Param("holder") EntityHolder<T> holder, @Param("ids") List<ID> ids) {
        return delete(holder, new Query().where(Criteria.where(holder.getIdProperty().getColumn()).in(ids)));
    }

    @DeleteProvider(type = MapperProvider.class, method = "delete")
    int delete(@Param("holder") EntityHolder<T> holder, @Param("query") Query query);


    /*=========================================================== SELECT ===========================================================*/
    default List<T> select(@Param("holder") EntityHolder<T> holder, @Param("ids") ID... ids) {
        return select(holder, Arrays.asList(ids));
    }

    default List<T> select(@Param("holder") EntityHolder<T> holder, @Param("ids") Collection<ID> ids) {
        Criteria<PropertyHolder> in = Criteria.where(holder.getRequiredIdProperty()).in(ids);
        return select(holder, new Query<PropertyHolder>().where((Criteria<PropertyHolder>) in));
    }

    @SelectProvider(type = MapperProvider.class, method = "select")
    List<T> select(@Param("holder") EntityHolder<T> holder, @Param("query") Query<PropertyHolder> query);

    default T selectOne(@Param("holder") EntityHolder<T> holder, @Param("id") ID id) {
        return selectOne(holder, new Query<PropertyHolder>().where((Criteria<PropertyHolder>) Criteria.where(holder.getRequiredIdProperty()).is(id)));
    }

    @SelectProvider(type = MapperProvider.class, method = "select")
    T selectOne(@Param("holder") EntityHolder<T> holder, @Param("query") Query<PropertyHolder> query);

    default long selectCount(@Param("holder") EntityHolder<T> holder) {
        return selectCount(holder, null);
    }

    @SelectProvider(type = MapperProvider.class, method = "selectCount")
    long selectCount(@Param("holder") EntityHolder<T> holder, @Param("query") Query<PropertyHolder> query);


    default boolean isExists(@Param("holder") EntityHolder<T> holder, @Param("id") ID id) {
        return selectOne(holder, id) != null;
    }

    default boolean isExists(@Param("holder") EntityHolder<T> holder, @Param("query") Query<PropertyHolder> query) {
        return selectCount(holder, query) > 0;
    }
}
