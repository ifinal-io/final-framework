package cn.com.likly.finalframework.mybatis.sql;

import cn.com.likly.finalframework.data.entity.Entity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-18 21:28
 * @since 1.0
 */
public interface InsertSql<ID extends Serializable, T extends Entity<ID>> extends Sql {

    InsertSql<ID, T> INSERT_INTO(Class<T> entity);

    InsertSql<ID, T> INSERT_COLUMNS();

    InsertSql<ID, T> INSERT_Cd();


}
