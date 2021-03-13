package org.ifinal.finalframework.mybatis.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ifinal.finalframework.mybatis.entity.Person;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

/**
 * PersonMapper.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {

}
