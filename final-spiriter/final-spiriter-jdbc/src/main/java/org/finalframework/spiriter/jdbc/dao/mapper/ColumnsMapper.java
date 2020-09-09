

package org.finalframework.spiriter.jdbc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.spiriter.jdbc.entity.Columns;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 20:18:01
 * @since 1.0
 */
@Mapper
public interface ColumnsMapper extends AbsMapper<Long, Columns> {
}
