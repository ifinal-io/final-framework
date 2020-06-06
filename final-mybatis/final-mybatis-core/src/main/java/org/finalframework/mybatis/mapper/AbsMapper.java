package org.finalframework.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.repository.Repository;
import org.finalframework.mybatis.sql.SelectSqlProvider;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 17:49
 * @since 1.0
 */
@SuppressWarnings("all")
public interface AbsMapper<ID extends Serializable, T extends IEntity<ID>> extends Repository<ID, T> {
    @Override
    @SelectProvider(SelectSqlProvider.class)
//    @Select("<script><include refid=\"org.finalframework.mybatis.mapper.AbsMapper.sql-select\"/></script>")
    List<T> select(@Param("table") String table, @Param("view") Class<?> view, @Param("ids") Collection<ID> ids, @Param("query") Query query);
}
