package cn.com.likly.finalframework.data.mybatis.provider;

import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mapping.Property;
import cn.com.likly.finalframework.data.provider.InsertProvider;
import cn.com.likly.finalframework.data.mybatis.handler.TypeHandlerRegistry;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 10:25
 * @since 1.0
 */
@Slf4j
public class DefaultInsertProvider<T> extends AbsProvider<T> implements InsertProvider<T> {

    private Entity<T> entity;
    private Collection<T> entities;

    public DefaultInsertProvider(TypeHandlerRegistry typeHandlerRegistry) {
        super(typeHandlerRegistry);
    }


    @Override
    public InsertProvider<T> INSERT_INTO(@NonNull Entity<T> entity) {
        this.entity = entity;
        return this;
    }


    @Override
    public InsertProvider<T> INSERT_VALUES(@NonNull Collection<T> entities) {
        this.entities = entities;
        return this;
    }

    private String getInsertColumns(Entity<T> entity) {
        return entity
                .stream()
                .filter(it -> !it.isTransient() && it.insertable())
                .map(Property::getColumn)
                .collect(Collectors.joining(",", "(", ")"));
    }

    private String getInsertValues(Entity<T> entity, Collection<T> entities) {
        return IntStream
                .range(0, entities.size())
                .mapToObj(index -> entity
                        .stream()
                        .filter(it -> !it.isTransient())
                        .filter(Property::insertable)
                        // #{list[${index}].${property},javaType={},typeHandler={}}
                        .map(it -> String.format("#{list[%d].%s %s}",
                                                 index,
                                                 it.getColumn(),
                                                 getJavaTypeAndTypeHandler(it)))
                        .collect(Collectors.joining(",", "(", ")")))
                .collect(Collectors.joining(","));
    }

    @Override
    public String provide() {

        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
          .append(getTable(entity))
          .append(getInsertColumns(entity))
          .append(" VALUES ")
          .append(getInsertValues(entity, entities));

        final String sql = sb.toString();
        logger.info("==> {}", sql);
        return sql;
    }
}
