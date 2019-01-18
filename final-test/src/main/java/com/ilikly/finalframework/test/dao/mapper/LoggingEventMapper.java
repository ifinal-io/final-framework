package com.ilikly.finalframework.test.dao.mapper;

import com.ilikly.finalframework.logging.entity.LoggingEvent;
import com.ilikly.finalframework.mybatis.mapper.AbsMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-16 21:54:17
 * @since 1.0
 */
@Mapper
public interface LoggingEventMapper extends AbsMapper<Long, LoggingEvent> {
}
