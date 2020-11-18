package org.finalframework.dashboard.mybaits.service.query;

import lombok.Data;
import org.finalframework.annotation.IQuery;
import org.finalframework.mybatis.mapper.AbsMapper;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 23:47:50
 * @since 1.0
 */
@Data
public class XmlMapperQuery {
    private Class<? extends AbsMapper> mapper;
    private String method;
    private String table;
    private boolean ignore;
    private boolean selective = false;
    private Class<? extends IQuery> query;
}
