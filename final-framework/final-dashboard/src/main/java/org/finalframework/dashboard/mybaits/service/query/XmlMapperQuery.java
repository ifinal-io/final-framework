package org.finalframework.dashboard.mybaits.service.query;

import lombok.Data;
import org.finalframework.annotation.IQuery;
import org.finalframework.mybatis.mapper.AbsMapper;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 23:47:50
 * @since 1.0
 */
@Data
public class XmlMapperQuery {
    private Class<? extends AbsMapper> mapper;
    private Method method;
    private Class<? extends IQuery> query;
}
