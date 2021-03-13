package org.ifinal.finalframework.mybatis.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ifinal.finalframework.mybatis.entity.User;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

/**
 * UserMapper.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends AbsMapper<Long, User> {

}
