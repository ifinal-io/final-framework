package org.ifinal.finalframework.example.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ifinal.finalframework.example.entity.Person;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface AbsPersonMapper extends AbsMapper<Long, Person> {

}
