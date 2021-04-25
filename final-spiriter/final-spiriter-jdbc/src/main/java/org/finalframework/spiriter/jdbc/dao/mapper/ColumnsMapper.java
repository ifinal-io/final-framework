package org.ifinal.finalframework.spiriter.jdbc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.spiriter.jdbc.entity.Columns;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Mapper
public interface ColumnsMapper extends AbsMapper<Long, Columns> {
}
