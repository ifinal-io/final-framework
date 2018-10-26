package cn.com.likly.finalframework.data.repository;

import cn.com.likly.finalframework.data.annotation.Command;
import cn.com.likly.finalframework.data.annotation.enums.CommandType;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.IEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:27
 * @since 1.0
 */
@SuppressWarnings({"unused", "unchecked"})
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

    /*=========================================== INSERT ===========================================*/
    @Command(CommandType.INSERT_ARRAY)
    int insert(T... entities);

    @Command(CommandType.INSERT_COLLECTION)
    int insert(Collection<T> entities);

    /*=========================================== SET ===========================================*/

    @Command(CommandType.UPDATE_ENTITIES_ARRAY)
    int update(T... entities);

    @Command(CommandType.UPDATE_ENTITIES_COLLECTION)
    int update(Collection<T> entities);

    @Command(CommandType.UPDATE_ID_ARRAY)
    int update(Update update, ID... ids);

    @Command(CommandType.UPDATE_ID_COLLECTION)
    int update(Update update, Collection<ID> ids);

    @Command(CommandType.UPDATE_QUERY)
    int update(Update update, Query query);

    /*=========================================== DELETE ===========================================*/

    @Command(CommandType.DELETE_ENTITIES_ARRAY)
    int delete(T... entities);

    @Command(CommandType.DELETE_ENTITIES_COLLECTION)
    int delete(List<T> entities);

    @Command(CommandType.DELETE_ID_ARRAY)
    int delete(ID... ids);

    @Command(CommandType.DELETE_ID_COLLECTION)
    int delete(Collection<ID> ids);

    @Command(CommandType.DELETE_QUERY)
    int delete(Query query);

    /*=========================================== SELECT ===========================================*/


    @Command(CommandType.SELECT_ID_ARRAY)
    List<T> select(ID... ids);

    @Command(CommandType.SELECT_ID_COLLECTION)
    List<T> select(Collection<ID> ids);

    @Command(CommandType.SELECT_QUERY)
    List<T> select(Query query);

    @Command(CommandType.SELECT_ONE_ID)
    T selectOne(ID id);

    @Command(CommandType.SELECT_ONE_QUERY)
    T selectOne(Query query);

    @Command(CommandType.SELECT_COUNT_QUERY)
    long selectCount(Query query);

    @Command(CommandType.SELECT_COUNT)
    long selectCount();

    @Command(CommandType.SELECT_IS_EXISTS_ID)
    boolean isExists(ID id);

    @Command(CommandType.SELECT_IS_EXISTS_QUERY)
    boolean isExists(Query query);


}
