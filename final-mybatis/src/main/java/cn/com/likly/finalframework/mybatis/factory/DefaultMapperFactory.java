package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.data.annotation.MultiColumn;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mapping.Property;
import cn.com.likly.finalframework.mybatis.EntityHolderCache;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import cn.com.likly.finalframework.mybatis.model.Association;
import cn.com.likly.finalframework.mybatis.model.Mapper;
import cn.com.likly.finalframework.mybatis.model.Result;
import cn.com.likly.finalframework.mybatis.model.ResultMap;
import org.apache.ibatis.type.TypeHandler;

import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 01:03:35
 * @since 1.0
 */
public class DefaultMapperFactory implements MapperFactory {
    private final TypeHandlerRegistry typeHandlerRegistry;
    private final EntityHolderCache cache = new EntityHolderCache();

    public DefaultMapperFactory(TypeHandlerRegistry typeHandlerRegistry) {
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    @Override
    public Mapper create(Class mapperClass) {
        @SuppressWarnings("unchecked")
        Entity<Property> entity = cache.get(mapperClass);
        return Mapper.builder(mapperClass)
                .addResultMap(createDefaultMap(entity))
                .build();
    }

    private ResultMap createDefaultMap(Entity<Property> entity) {
        ResultMap.Builder builder = ResultMap.builder(entity.getType().getSimpleName(), entity.getType());
        entity.stream().filter(it -> !it.isTransient() && !it.hasAnnotation(MultiColumn.class))
                .map(this::createResult)
                .forEach(builder::addResult);

        entity.stream().filter(it -> !it.isTransient() && it.hasAnnotation(MultiColumn.class))
                .map(it -> {
                    MultiColumn multiColumn = it.findAnnotation(MultiColumn.class);
                    if (multiColumn == null) throw new RuntimeException("");
                    Class entityClass = entity.getType();
                    @SuppressWarnings("unchecked")
                    Entity<Property> multiEntity = Entity.from(entityClass);
                    Association.Builder b = Association.builder(it.getName())
                            .column(it.getColumn())
                            .javaType(it.getType());
                    Arrays.stream(multiColumn.properties())
                            .map(multiEntity::getRequiredPersistentProperty)
                            .map(this::createResult)
                            .forEach(b::addResult);

                    return b.build();
                }).forEach(builder::addAssociation);

        return builder.build();
    }

    private Result createResult(Property property) {
        final Class javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class collectionType = property.isCollectionLike() ? property.getType() : null;
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(javaType, collectionType, property.getPersistentType());
        return Result.builder(property.getName(), property.getColumn())
                .javaType(javaType)
                .typeHandler(typeHandler)
                .idResult(property.isIdProperty())
                .build();
    }


}
