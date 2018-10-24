package cn.com.likly.finalframework.data.repository;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.Entity;

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
public interface Repository<ID extends Serializable, T extends Entity<ID>> {

    /*=========================================== INSERT ===========================================*/
    int insert(T... entities);

    int insert(Collection<T> entities);

    /*=========================================== SET ===========================================*/

    int update(T... entities);

    int update(Collection<T> entities);

    int update(Update update, ID... ids);

    int update(Update update, Collection<ID> ids);

    int update(Update update, Query query);

    /*=========================================== DELETE ===========================================*/

    int delete(T... entities);

    int delete(List<T> entities);

    int delete(ID... ids);

    int delete(Collection<ID> ids);

    int delete(Query query);

    /*=========================================== SELECT ===========================================*/

    List<T> select(ID... ids);

    List<T> select(Collection<ID> ids);

    List<T> select(Query query);

    T selectOne(ID id);

    T selectOne(Query query);

    long selectCount(Query query);

    long selectCount();

    boolean isExists(ID id);

    boolean isExists(Query query);


}
