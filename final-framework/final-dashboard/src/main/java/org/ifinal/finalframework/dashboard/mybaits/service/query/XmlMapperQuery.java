package org.ifinal.finalframework.dashboard.mybaits.service.query;

import org.ifinal.finalframework.core.annotation.IQuery;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class XmlMapperQuery {

    private Class<? extends AbsMapper<?, ?>> mapper;

    private String method;

    private String table;

    private boolean ignore;

    private boolean selective = false;

    private Class<? extends IQuery> query;

}
