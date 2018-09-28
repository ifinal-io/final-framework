package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.mybatis.holder.EntityHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

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
    @Override
    public ResultMap create(EntityHolder holder, Configuration configuration) {
        return new ResultMap.Builder(
                configuration,
                holder.getPrimaryKey().getColumn(),
                holder.getEntityClass(),
                holder.getProperties().stream()
                        .map(
                                propertyHolder -> new ResultMapping.Builder(
                                        configuration,
                                        propertyHolder.getProperty(),
                                        propertyHolder.getColumn(),
                                        propertyHolder.getTypeHandler()
                                ).build()
                        ).collect(Collectors.toList())
        ).build();
    }
}
