package com.ilikly.finalframework.test.dao.mapper;

import com.ilikly.finalframework.mybatis.mapper.AbsMapper;
import com.ilikly.finalframework.test.entity.Person;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-21 21:49:02
 * @since 1.0
 */
@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {
}
