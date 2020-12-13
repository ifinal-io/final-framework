package org.ifinal.finalframework.dashboard.mybaits.service;

import java.util.List;
import org.ifinal.finalframework.dashboard.mybaits.service.query.MapperQuery;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MapperService {

    List<Class<? extends AbsMapper>> query(@NonNull MapperQuery query);

}
