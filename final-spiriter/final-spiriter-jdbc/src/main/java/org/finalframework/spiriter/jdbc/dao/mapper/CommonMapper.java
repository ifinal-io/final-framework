package org.ifinal.finalframework.spiriter.jdbc.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Mapper
public interface CommonMapper {

    @Select("show databases")
    List<String> showDatabases();

    @Select("select database()")
    String selectDatabase();

    @Select("select version()")
    String selectVersion();

    @Select("show tables")
    List<String> showTables();


}
