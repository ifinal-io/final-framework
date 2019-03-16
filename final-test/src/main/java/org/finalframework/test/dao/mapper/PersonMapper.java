package org.finalframework.test.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.test.entity.Person;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-21 21:49:02
 * @since 1.0
 */
@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {
}
