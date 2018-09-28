package cn.com.likly.finalframework.mybatis.mapper;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.annotation.Command;
import cn.com.likly.finalframework.mybatis.annotation.CommandType;
import cn.com.likly.finalframework.mybatis.criteria.Criteria;
import cn.com.likly.finalframework.mybatis.criteria.SetCriteria;
import cn.com.likly.finalframework.mybatis.holder.EntityHolder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 17:49
 * @since 1.0
 */
@SuppressWarnings("all")
@Mapper
public interface DefaultMapper<ID, T extends Entity<ID>> {

    @Command(CommandType.INSERT_ARRAY)
    Integer insert(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("array") T... entities);

    @Command(CommandType.INSERT_LIST)
    Integer insert(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("list") List<T> entities);

    @Command(CommandType.INSERT_ARRAY)
    Integer insertUuid(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("array") T... entities);

    @Command(CommandType.INSERT_LIST)
    Integer insertUuid(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("list") List<T> entities);

    @Command(CommandType.INSERT_ARRAY)
    Integer insertMd5(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("array") T... entities);

    @Command(CommandType.INSERT_LIST)
    Integer insertMd5(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("list") List<T> entities);

    @Command(CommandType.INSERT_ARRAY)
    Integer insertOther(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("array") T... entities);

    @Command(CommandType.INSERT_LIST)
    Integer insertOther(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("list") List<T> entities);

    @Command(CommandType.UPDATE_ENTITY)
    Integer update(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("entity") T entity);

    @Command(CommandType.UPDATE_CRITERIA)
    Integer update(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("criteria") Criteria criteria);

    @Command(CommandType.UPDATE_ID_SETS)
    Integer update(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("id") ID id, @Param("sets") SetCriteria... setCriteria);

    @Command(CommandType.UPDATE_CRITERIA_SETS)
    Integer update(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("criteria") Criteria criteria, @Param("sets") SetCriteria... setCriteria);

    @Command(CommandType.DELETE_ID_ARRAY)
    Integer delete(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("ids") ID... ids);

    @Command(CommandType.DELETE_ID_ARRAY)
    Integer delete(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("ids") List<ID> ids);

    @Command(CommandType.DELETE_ENTITIES)
    Integer delete(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("entities") T... entities);

    @Command(CommandType.DELETE_CRITERIA)
    Integer delete(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("criteria") Criteria criteria);

    @Command(CommandType.SELECT_ID_ARRAY)
    List<T> select(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("ids") ID... ids);

    @Command(CommandType.SELECT_ID_LIST)
    List<T> select(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("ids") List<ID> ids);

    @Command(CommandType.SELECT_CRITERIA)
    List<T> select(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("criteria") Criteria criteria);

    @Command(CommandType.SELECT_ONE_ID)
    T selectOne(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("id") ID id);

    @Command(CommandType.SELECT_ONE_CRITERIA)
    T selectOne(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("criteria") Criteria criteria);

    @Command(CommandType.SELECT_COUNT)
    Long selectCount(@Param("command") String command, @Param("holder") EntityHolder holder, @Param("criteria") Criteria criteria);

}
