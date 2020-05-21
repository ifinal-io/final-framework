package org.finalframework.spiriter.jdbc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    String database();

    @Select("select version()")
    String version();


}
