

package org.finalframework.spiriter.jdbc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.spiriter.jdbc.entity.Tables;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 23:04:13
 * @since 1.0
 */
@Mapper
public interface TablesMapper extends AbsMapper<Long, Tables> {

}

