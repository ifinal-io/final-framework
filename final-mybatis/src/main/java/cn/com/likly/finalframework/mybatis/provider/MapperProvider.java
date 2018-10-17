package cn.com.likly.finalframework.mybatis.provider;

import cn.com.likly.finalframework.data.entity.Entity;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 09:41
 * @since 1.0
 */

@Component
public class MapperProvider<ID extends Serializable, T extends Entity<ID>> implements ApplicationContextAware {

    private static TypeHandlerRegistry typeHandlerRegistry;


    public String insert(Map<String, Object> args) {
        EntityHolder<T, ? extends PropertyHolder> holder = (EntityHolder<T, ? extends PropertyHolder>) args.get("holder");
        Collection<T> list = args.containsKey("array") ? Arrays.asList((T[]) args.get("array"))
                : (Collection<T>) args.get("list");

        final StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ")
                .append(getTable(holder))
                .append(getInsertColumns(holder))
                .append(" VALUES")
                .append(getInsertValues(holder, list, args.containsKey("array") ? "array" : "list"));


        return sb.toString();


    }

    protected String getTable(EntityHolder<T, ? extends PropertyHolder> holder) {
        return holder.getTable();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        typeHandlerRegistry = applicationContext.getBean(TypeHandlerRegistry.class);
    }

    protected String getInsertColumns(EntityHolder<T, ? extends PropertyHolder> holder) {
        return holder.stream().filter(it -> !it.isTransient())
                .filter(PropertyHolder::insertable)
                .map(PropertyHolder::getColumn)
                .collect(Collectors.joining(",", "(", ")"));
    }

    protected String getInsertValues(EntityHolder<T, ? extends PropertyHolder> holder, Collection<T> entities, String prefix) {
        return IntStream.range(0, entities.size())
                .mapToObj(index -> holder.stream().filter(it -> !it.isTransient())
                        .filter(PropertyHolder::insertable)
                        .map(it -> String.format("#{%s[%d].%s,typeHandler=%s}",
                                prefix,
                                index,
                                it.getColumn(),
                                (it.isCollectionLike() ?
                                        typeHandlerRegistry.getTypeHandler(it.getComponentType(), it.getType(), it.getPersistentType())
                                        : typeHandlerRegistry.getTypeHandler(it.getType(), null, it.getPersistentType())
                                ).getClass().getName())
                        ).collect(Collectors.joining(",", "(", ")")))
                .collect(Collectors.joining(","));


    }

}
