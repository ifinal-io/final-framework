package org.finalframework.document.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.mapping.Entity;
import org.finalframework.document.api.service.EntityService;
import org.finalframework.document.api.service.query.EntityQuery;
import org.finalframework.util.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Service;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:15:50
 * @since 1.0
 */
@Service
public class EntityServiceImpl implements EntityService {
    public static final Logger logger = LoggerFactory.getLogger(EntityServiceImpl.class);
    private final Map<String, Class<?>> entitiesMap = SpringFactoriesLoader.loadFactoryNames(IEntity.class, getClass().getClassLoader())
            .stream()
            .collect(Collectors.toMap(Function.identity(), Classes::forName));

    @Override
    public List<Class<?>> query(EntityQuery query) {

        return entitiesMap.values()
                .stream()
                .filter(entity -> {
                    if (Assert.nonEmpty(query.getName())) {
                        return entity.getCanonicalName().toUpperCase().contains(query.getName().toUpperCase());
                    } else {
                        return true;
                    }


                })
                .collect(Collectors.toList());


    }

    @Override
    public Entity<?> entity(Class<?> entity) {
        return Entity.from(entity);
    }
}

