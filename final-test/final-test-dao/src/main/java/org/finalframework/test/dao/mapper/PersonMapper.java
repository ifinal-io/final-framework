package org.finalframework.test.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.test.entity.Person;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-03 18:07:54
 * @since 1.0
 */
@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {
}
