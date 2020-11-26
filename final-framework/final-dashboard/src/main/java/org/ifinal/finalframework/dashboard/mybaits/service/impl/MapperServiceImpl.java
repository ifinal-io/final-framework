package org.ifinal.finalframework.dashboard.mybaits.service.impl;

import org.ifinal.finalframework.dashboard.mybaits.service.MapperService;
import org.ifinal.finalframework.dashboard.mybaits.service.query.MapperQuery;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.util.Proxies;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
class MapperServiceImpl implements MapperService {

    private final List<AbsMapper<?, ?>> mappers;

    public MapperServiceImpl(ObjectProvider<List<AbsMapper<?, ?>>> mappersProviers) {
        this.mappers = mappersProviers.getIfAvailable();
    }

    @Override
    public List<Class<? extends AbsMapper>> query(@NotNull MapperQuery query) {

        return mappers.stream()
                .map(Proxies::targetClass)
                .map(clazz -> (Class<? extends AbsMapper>) clazz)
                .collect(Collectors.toList());

    }
}
