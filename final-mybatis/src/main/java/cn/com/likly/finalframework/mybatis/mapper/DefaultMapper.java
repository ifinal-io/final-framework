package cn.com.likly.finalframework.mybatis.mapper;

import cn.com.likly.finalframework.data.annotation.enums.CommandType;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.Entity;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.mybatis.provider.MapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    default int insert(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("array") T... entities) {
        return insert(command, holder, Arrays.asList(entities));
    }

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insert(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("list") Collection<T> entities);

    int insertUuid(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("array") T... entities);

    int insertUuid(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("list") Collection<T> entities);

    int insertMd5(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("array") T... entities);

    int insertMd5(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("list") Collection<T> entities);

    int insertOther(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("array") T... entities);

    int insertOther(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("list") Collection<T> entities);

    /*=========================================================== UPDATE ===========================================================*/

    int update(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("holder") T entity);

    int update(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("update") Update update, @Param("ids") ID... ids);

    int update(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("update") Update update, @Param("ids") Collection<ID>... ids);

    int update(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("update") Update update, @Param("query") Query query);

    /*=========================================================== DELETE ===========================================================*/

    int delete(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("array") T... entities);

    int delete(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("list") Collection<T> entities);

    int delete(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("ids") ID... ids);

    int delete(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("ids") List<ID> ids);

    int delete(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("query") Query query);


    /*=========================================================== SELECT ===========================================================*/
    List<T> select(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("ids") ID... ids);

    List<T> select(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("ids") Collection<ID> ids);

    List<T> select(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("query") Query query);

    T selectOne(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("id") ID id);

    T selectOne(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("query") Query query);

    long selectCount(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("query") Query query);

    long selectCount(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> entity);

    default boolean isExists(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("id") ID id) {
        return selectOne(command, holder, id) != null;
    }

    default boolean isExists(@Param("command") CommandType command, @Param("holder") EntityHolder<T, ? extends PropertyHolder> holder, @Param("query") Query query) {
        return selectCount(command, holder, query) > 0;
    }
}
