package org.finalframework.spiriter.jdbc.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 22:49:45
 * @since 1.0
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
