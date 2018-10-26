package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
                holder.getIdProperty().getColumn(),
                holder.getType(),
                holder.stream()
                        .filter(it -> !it.isTransient())
                        .map(
                                propertyHolder -> new ResultMapping.Builder(
                                        configuration,
                                        propertyHolder.getName(),
                                        propertyHolder.getColumn(),
                                        (propertyHolder).isCollectionLike() ?
                                                typeHandlerRegistry.getTypeHandler(propertyHolder.getComponentType(), propertyHolder.getType(), propertyHolder.getPersistentType())
                                                : typeHandlerRegistry.getTypeHandler(propertyHolder.getType(), null, propertyHolder.getPersistentType())
                                ).build()
                        ).collect(Collectors.toList())
        ).build();
    }

}
