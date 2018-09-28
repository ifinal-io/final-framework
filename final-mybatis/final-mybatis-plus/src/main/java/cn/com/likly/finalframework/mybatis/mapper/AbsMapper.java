package cn.com.likly.finalframework.mybatis.mapper;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.annotation.Command;
import cn.com.likly.finalframework.mybatis.annotation.CommandType;
import cn.com.likly.finalframework.mybatis.criteria.Criteria;
import cn.com.likly.finalframework.mybatis.criteria.SetCriteria;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 17:49
 * @since 1.0
 */
@SuppressWarnings("all")
public interface AbsMapper<ID, T extends Entity<ID>> {

    @Command(CommandType.INSERT_ARRAY)
    Integer insert(T... entities);

    @Command(CommandType.INSERT_LIST)
    Integer insert(List<T> entities);

    @Command(CommandType.UPDATE_ENTITY)
    Integer update(T entity);

    @Command(CommandType.UPDATE_CRITERIA)
    Integer update(Criteria criteria);

    @Command(CommandType.UPDATE_ID_SETS)
    Integer update(ID id, SetCriteria... setCriteria);

    @Command(CommandType.UPDATE_CRITERIA_SETS)
    Integer update(Criteria criteria, SetCriteria... setCriteria);

    @Command(CommandType.DELETE_ID_ARRAY)
    Integer delete(ID... ids);

    @Command(CommandType.DELETE_ID_LIST)
    Integer delete(List<ID> ids);

    @Command(CommandType.DELETE_ENTITIES)
    Integer delete(T... entities);

    @Command(CommandType.DELETE_CRITERIA)
    Integer delete(Criteria criteria);


    @Command(CommandType.SELECT_ID_ARRAY)
    List<T> select(ID... ids);

    @Command(CommandType.SELECT_ID_LIST)
    List<T> select(List<ID> ids);

    @Command(CommandType.SELECT_CRITERIA)
    List<T> select(Criteria criteria);

    @Command(CommandType.SELECT_ONE_ID)
    T selectOne(ID id);

    @Command(CommandType.SELECT_ONE_CRITERIA)
    T selectOne(Criteria criteria);

    @Command(CommandType.SELECT_COUNT)
    Long selectCount(Criteria criteria);

}
