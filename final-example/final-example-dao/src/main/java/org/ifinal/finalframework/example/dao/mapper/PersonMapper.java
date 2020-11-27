package org.ifinal.finalframework.example.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ifinal.finalframework.example.entity.Person;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

import javax.annotation.Generated;

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
}
