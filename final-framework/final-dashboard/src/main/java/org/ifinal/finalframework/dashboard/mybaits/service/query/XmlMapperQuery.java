package org.ifinal.finalframework.dashboard.mybaits.service.query;

import lombok.Data;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;

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
