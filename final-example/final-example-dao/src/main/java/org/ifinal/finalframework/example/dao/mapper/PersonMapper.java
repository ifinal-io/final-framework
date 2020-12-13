package org.ifinal.finalframework.example.dao.mapper;

import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.example.entity.Person;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.mybatis.sql.provider.SelectSqlProvider;

/**
 * @author finalframework
 * @version 1.0.0
 */
@Mapper
@Generated(
    value = "org.ifinal.finalframework.auto.mybatis.processor.AutoMapperGeneratorProcessor",
    date = "2020-11-28 02:58:58"
)
public interface PersonMapper extends AbsMapper<Long, Person> {

    @Override
    @ResultMap("PersonMap")
    @SelectProvider(SelectSqlProvider.class)
    List<Person> select(@Param("table") String table, @Param("view") Class<?> view, @Param("ids") Collection<Long> ids,
        @Param("query") IQuery query);

}
