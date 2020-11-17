package org.finalframework.dashboard.mybaits.service;

import org.finalframework.dashboard.mybaits.service.query.MapperQuery;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 22:33:45
 * @since 1.0
 */
public interface MapperService {

    List<Class<? extends AbsMapper>> query(@NonNull MapperQuery query);


}
