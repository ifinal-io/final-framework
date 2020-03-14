package org.finalframework.test.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.test.entity.Person;

@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {

}
