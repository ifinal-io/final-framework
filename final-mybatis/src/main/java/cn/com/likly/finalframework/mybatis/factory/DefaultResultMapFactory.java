package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.data.annotation.MultiColumn;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mapping.Property;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:59
 * @since 1.0
 */
@Slf4j
@Component
public class DefaultResultMapFactory implements ResultMapFactory {

    @Resource
    private TypeHandlerRegistry typeHandlerRegistry;

    @Override
    public ResultMap create(Entity<?> holder, Configuration configuration) {

        return new ResultMap.Builder(
                configuration,
                holder.getRequiredIdProperty().getColumn(),
                holder.getType(),
                holder.stream()
                        .filter(it -> !it.isTransient())
                        .map(
                                propertyHolder -> buildResultMapping(propertyHolder, configuration, null)
                        ).collect(Collectors.toList())
        ).build();
    }

    private ResultMapping buildResultMapping(Property property, Configuration configuration, String prefix) {
        MultiColumn multiColumn = property.findAnnotation(MultiColumn.class);

        if (multiColumn == null) {
            return new ResultMapping.Builder(
                    configuration,
                    property.getName(),
                    property.getColumn(),
                    (property).isCollectionLike() ?
                            typeHandlerRegistry.getTypeHandler(property.getComponentType(), property.getType(), property.getPersistentType())
                            : typeHandlerRegistry.getTypeHandler(property.getType(), null, property.getPersistentType())
            ).columnPrefix(prefix)
                    .build();
        }

        Entity entity = Entity.from(property.getType());
        List<ResultMapping> resultMappings = Arrays.stream(multiColumn.properties())
                .map(it -> buildResultMapping((Property) entity.getRequiredPersistentProperty(it), configuration, property.getName()))
                .collect(Collectors.toList());

        return new ResultMapping.Builder(
                configuration,
                property.getName(),
                property.getColumn(),
                configuration.getTypeHandlerRegistry().getUnknownTypeHandler()
        ).composites(resultMappings)
                .build();


    }

}
