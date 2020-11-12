package org.finalframework.example.dao.mapper;

import org.finalframework.example.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.finalframework.mybatis.mapper.AbsMapper;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/12 20:47:14
 * @since 1.0
 */
@Mapper
public interface AbsPersonMapper extends AbsMapper<Long, Person> {
}
