package org.ifinal.finalframework.dashboard.mybaits.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.ifinal.finalframework.dashboard.mybaits.service.MapperService;
import org.ifinal.finalframework.dashboard.mybaits.service.query.MapperQuery;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.util.Proxies;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
class MapperServiceImpl implements MapperService {

    private final List<AbsMapper<?, ?>> mappers;

    public MapperServiceImpl(final ObjectProvider<List<AbsMapper<?, ?>>> mappersProviers) {

        this.mappers = mappersProviers.getIfAvailable();
    }

    @Override
    public List<Class<? extends AbsMapper>> query(final @NotNull MapperQuery query) {

        return mappers.stream()
            .map(Proxies::targetClass)
            .map(clazz -> (Class<? extends AbsMapper>) clazz)
            .collect(Collectors.toList());

    }

}
