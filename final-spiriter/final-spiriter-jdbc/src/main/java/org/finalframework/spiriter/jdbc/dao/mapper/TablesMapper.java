package org.ifinal.finalframework.spiriter.jdbc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.spiriter.jdbc.entity.Tables;


/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Mapper
public interface TablesMapper extends AbsMapper<Long, Tables> {

}

